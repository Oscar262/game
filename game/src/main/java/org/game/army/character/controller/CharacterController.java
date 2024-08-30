package org.game.army.character.controller;

import org.game.army.character.model.Character;
import org.game.army.character.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Validated
@EnableWebMvc
@RestController
public class CharacterController {

    @Autowired
    private CharacterService characterService;


    @PostMapping("/character")
    public Character character(){
        return characterService.newCharacter();
    }
}
