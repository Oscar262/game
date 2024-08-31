package org.game.army.character.controller;

import org.game.army.character.input.CharacterSearch;
import org.game.army.character.model.Character;
import org.game.army.character.service.CharacterService;
import org.game.utils.OffsetPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("characters")
    public Page<Character> getAll(OffsetPagination offsetPagination, CharacterSearch characterSearch){
        return characterService.getAll(offsetPagination, characterSearch);
    }
}
