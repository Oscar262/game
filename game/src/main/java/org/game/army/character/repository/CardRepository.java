package org.game.army.character.repository;

import org.game.army.character.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {


    Card findByType(Card.Type type);
}
