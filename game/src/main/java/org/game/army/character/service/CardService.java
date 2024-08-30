package org.game.army.character.service;

import org.game.army.character.model.Card;
import org.game.army.character.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card findByType(Card.Type type){
        return cardRepository.findByType(type);
    }
}
