package org.game.army.character.controller;

import org.game.army.character.input.CharacterSearch;
import org.game.army.character.model.Character;
import org.game.army.character.service.CharacterService;
import org.game.utils.OffsetPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Validated
@EnableWebMvc
@RestController
public class CharacterController {

    @Autowired
    private CharacterService characterService;


    @GetMapping("/character/{character_id}")
    public Character getCharacter(@PathVariable("character_id") Long characterId){
        return characterService.getCharacter(characterId).get();
    }

    @GetMapping("/character")
    public Page<Character> getAll(OffsetPagination offsetPagination, CharacterSearch characterSearch){
        return characterService.getAll(offsetPagination, characterSearch);
    }

    @PostMapping("/character")
    public Character character(){
        return characterService.newCharacter();
    }

    @PostMapping("/character_epic")
    public Character characterEpic(@RequestBody Character character, @RequestPart("file") MultipartFile file){
        return characterService.epicCharacter(character, file);
    }

    @PutMapping(path = "/character/{character_id}/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public Character updateImage(@PathVariable("character_id") Long characterId, @RequestPart("file") MultipartFile file) {
        return characterService.saveImage(characterId, file);
    }
}
