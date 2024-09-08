package org.game.army.character.service;

import org.game.army.character.input.CharacterSearch;
import org.game.army.character.model.Card;
import org.game.army.character.model.InventoryCharacter;
import org.game.army.character.model.Skill;
import org.game.army.character.repository.CharacterRepository;
import org.game.army.character.model.Character;
import org.game.army.character.utils.CharactersVariables;
import org.game.auth.model.User;
import org.game.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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


    public Optional<Character> getCharacter(Long characterId) {
        User user = userService.getUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");

        return characterRepository.findById(characterId);
    }


    public Page<Character> getAll(Pageable pageable, CharacterSearch characterSearch) {
        User user = userService.getUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");
        characterSearch.setUserId("eq:" + user.getId());

        return characterRepository.findAll(characterSearch.build(), pageable);
    }

    public Character newCharacter() {
        User user = userService.getUser();
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");

        Character character = new Character();
        character.setUser(user);


        //TODO: hacer con llamadas a ia
        character.setImage(null);

        //TODO: hacer con llamadas a ia
        character.setGender(charactersVariables.getGender());
        if (character.getGender() == Character.Gender.MALE) character.setName(charactersVariables.getMaleName());
        else character.setName(charactersVariables.getFemaleName());
        character.setLastName(charactersVariables.getLastName());

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
        character.setImage(fileBytes);
        return characterRepository.save(character);
    }

    public Character epicCharacter(Character character, MultipartFile image){
        User user = userService.getUser();
        character.setUser(user);

        byte[] fileBytes = null;
        try {
            fileBytes = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        character.setImage(fileBytes);
        return characterRepository.save(character);
    }
}
