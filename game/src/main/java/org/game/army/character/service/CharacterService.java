package org.game.army.character.service;

import org.game.army.character.model.Card;
import org.game.army.character.repository.CharacterRepository;
import org.game.army.character.model.Character;
import org.game.army.character.utils.CharactersVariables;
import org.game.auth.model.User;
import org.game.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

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



    public Character newCharacter() {
        //TODO: falta get user
        User user = userService.getUser();
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");

        Character character = new Character();
        character.setUser(user);

        Map<Character.Skill, Long> skills = new HashMap<>();
        Map<Character.Attribute, Long> attributes = new HashMap<>();
        Map<Character.Attribute, Long> maxAttributes = new HashMap<>();

        //TODO: hacer con llamadas a ia
        character.setGender(charactersVariables.getGender());
        if (character.getGender() == Character.Gender.MALE)
            character.setName(charactersVariables.getMaleName());
        else
            character.setName(charactersVariables.getFemaleName());

        character.setExperience(0L);
        character.setToNextLevel(charactersVariables.totalXPNextLevel(character.getLevel()));
        Card card = cardService.findByType(charactersVariables.getCharacterCard(user.getLevel()));
        character.setCard(card);
        character.setLevel(card.getType().getMinLevel());
        //TODO: hacer con llamadas a ia
        character.setImage(null);
        //          image
        //TODO: dependiendo del tipo de base se deben asignar porcentages diferentes para la seleccion
        character.setType(null);
        //          type
        //TODO: dependiendo del nivel y del tipo
        character.setSkills(null);
        //  skills
        //TODO: dependiendo del nivel y del tipo
        character.setAttributes(null);
        //          attributes
        //TODO: completamente aleatorio,
        character.setMaxAttributes(null);
        //  maxAttributes
        //TODO: aleatorio entre los tipos
        character.setSubType(null);
        //          subType
        //TODO: completamente aleatorio
        //  profession


        return characterRepository.save(character);
    }
}
