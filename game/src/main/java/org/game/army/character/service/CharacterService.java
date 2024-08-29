package org.game.army.character.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.game.army.character.CharacterRepository;
import org.game.army.character.model.Character;
import org.game.auth.model.User;
import org.game.auth.service.UserService;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.Map;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private UserService userService;


    public Character newCharacter() {
        //TODO: falta get user
        User user = userService.getUser();
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect user");

        Character character = new Character();
        character.setUser(user);

        Map<String, Long> skills = new HashMap<>();
        Map<Character.Attribute, Long> attributes = new HashMap<>();
        Map<Character.Attribute, Long> maxAttributes = new HashMap<>();



    //  name
    //          image
    //  level
    //          experience
    //  toNextLevel
    //          type
    //  skills
    //          attributes
    //  maxAttributes
    //          subType
    //  profession
    //  card


        return characterRepository.save(character);
    }
}
