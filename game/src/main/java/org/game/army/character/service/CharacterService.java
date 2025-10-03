package org.game.army.character.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.game.army.character.input.CharacterSearch;
import org.game.army.character.model.Card;
import org.game.army.character.model.Character;
import org.game.army.character.model.InventoryCharacter;
import org.game.army.character.model.Skill;
import org.game.army.character.repository.CharacterRepository;
import org.game.army.character.utils.CharactersVariables;
import org.game.auth.model.User;
import org.game.auth.service.UserService;
import org.game.utils.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CharactersVariables charactersVariables;

    @Autowired
    private CardService cardService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private InventoryCharacterService inventoryCharacterService;

    @Autowired
    private AiService iaService;

    @Autowired
    private Random random;

    public Optional<Character> getCharacter(Long characterId) {
        User user = userService.getUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");

        return characterRepository.findById(characterId);
    }


    public Page<Character> getAll(Pageable pageable, CharacterSearch characterSearch) {
        User user = userService.getUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");
        characterSearch.setUserId("eq:" + user.getId());

        return characterRepository.findAll(characterSearch, pageable);
    }

    public Character newCharacter() {
        User user = userService.getUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");

        Character character = new Character();
        character.setUser(user);

        character.setExperience(0L);
        Card card = cardService.findByType(charactersVariables.getCharacterCard(user.getLevel()));
        character.setCard(card);
        character.setLevel(card.getType().getMinLevel());

        character.setToNextLevel(charactersVariables.totalXPNextLevel(character.getLevel()));
        character.setType(charactersVariables.getType(user.getMainBase().getMainBaseType()));
        List<Skill> skills = skillService.getSkillByCharacterType(character.getType());
        character.setSubType(charactersVariables.getSubTypes(character.getCard().getType(), character.getType()));
        character.setSkills(charactersVariables.getSkills(character.getLevel(), skills));
        character.setAttributes(charactersVariables.getAttributes(character.getLevel()));
        character.setMaxAttributes(charactersVariables.getMaxAttributes(character.getAttributes()));
        character.setProfession(charactersVariables.getProfessions(character.getLevel()));
        InventoryCharacter inventoryCharacter = new InventoryCharacter();
        inventoryCharacter = inventoryCharacterService.save(inventoryCharacter);
        character.setInventoryCharacter(inventoryCharacter);

        String jsonDescirption = null;
        try {
            jsonDescirption = iaService.generateCharacter(character, random.nextInt());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        // Parseamos el JSON como un objeto genérico
        JsonObject jsonObject = gson.fromJson(jsonDescirption, JsonObject.class);

        // Creamos un nuevo Character y asignamos los campos
        if (jsonObject.has("name")) {
            character.setName(jsonObject.get("name").getAsString());
        }
        if (jsonObject.has("surname")) {
            character.setLastName(jsonObject.get("surname").getAsString());
        }
        if (jsonObject.has("gender")) {
            Map<String, String> genderMap = Map.of(
                    "masculino", "MALE",
                    "femenino", "FEMALE",
                    "other", "OTHER"
            );
            try {
                String gender = jsonObject.get("gender").getAsString().toLowerCase();
                character.setGender(Character.Gender.valueOf(genderMap.get(gender)));
            } catch (Exception e) {
                character.setGender(Character.Gender.OTHER);
            }
        }
        if (jsonObject.has("description")) {
            JsonObject descObj = jsonObject.getAsJsonObject("description");

            // Guardamos la descripción actual
            if (descObj.has("current")) character.setDescription(descObj.get("current").getAsString());

            Map<Long, Pair<Boolean, byte[]>> imagesByLevel = new HashMap<>();

            // Lista de niveles
            Map<Long, String> levels = new HashMap<>();
            if (descObj.has("level1")) levels.put(1L, descObj.get("level1").getAsString());
            if (descObj.has("level2")) levels.put(2L, descObj.get("level2").getAsString());
            if (descObj.has("level3")) levels.put(3L, descObj.get("level3").getAsString());
            if (descObj.has("level4")) levels.put(4L, descObj.get("level4").getAsString());
            if (descObj.has("level5")) levels.put(5L, descObj.get("level5").getAsString());
            if (descObj.has("level6")) levels.put(6L, descObj.get("level6").getAsString());

            CompletableFuture.allOf(levels.entrySet().stream()
                    .map(entry -> CompletableFuture.runAsync(() -> {
                        long levelToGenerate = entry.getKey();
                        String levelDescription = entry.getValue();

                        // Construimos prompt solo para el nivel que se va a generar
                        StringBuilder promptBuilder = new StringBuilder();
                        promptBuilder.append("Genera una imagen de un personaje de fantasía.\n");
                        promptBuilder.append("Nombre: ").append(character.getName())
                                .append(" ").append(character.getLastName()).append("\n\n");
                        promptBuilder.append("Descripción y apariencia del personaje:\n");
                        promptBuilder.append(levelDescription).append("\n");

                        String fullPrompt = promptBuilder.toString();
                        byte[] image = null;
                        try {
                            image = iaService.generateCharacterImage(fullPrompt);
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        synchronized (imagesByLevel) {
                            if (levelToGenerate == 1)
                                imagesByLevel.put(levelToGenerate, Pair.of(true, image));
                            else
                                imagesByLevel.put(levelToGenerate, Pair.of(false, image));
                        }

                        System.out.println("Prompt usado para nivel " + levelToGenerate + ":\n" + fullPrompt);

                    })).toArray(CompletableFuture[]::new)).join();

            character.setImage(imagesByLevel);

        }
        return characterRepository.save(character);
    }

    public Character saveImage(Long characterId, MultipartFile multipart) {
        Optional<Character> optionalCharacter = getCharacter(characterId);
        if (optionalCharacter.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not fount");
        Character character = optionalCharacter.get();
        byte[] fileBytes = null;
        try {
            fileBytes = multipart.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //character.setImage(fileBytes);
        return characterRepository.save(character);
    }

    public Character epicCharacter(Character character, MultipartFile image) {
        User user = userService.getUser();
        character.setUser(user);

        byte[] fileBytes = null;
        try {
            fileBytes = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //character.setImage(fileBytes);
        return characterRepository.save(character);
    }
}
