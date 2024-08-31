package org.game.army.character.repository;

import org.game.army.character.model.InventoryCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryCharacterRepository extends JpaRepository<InventoryCharacter, Long> {
}
