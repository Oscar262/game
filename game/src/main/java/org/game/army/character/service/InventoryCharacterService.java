package org.game.army.character.service;

import org.game.army.character.model.InventoryCharacter;
import org.game.army.character.repository.InventoryCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryCharacterService {

    @Autowired
    private InventoryCharacterRepository inventoryCharacterRepository;


    public InventoryCharacter save(InventoryCharacter inventoryCharacter){
        return inventoryCharacterRepository.save(inventoryCharacter);
    }
}
