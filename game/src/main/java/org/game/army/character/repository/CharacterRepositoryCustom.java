package org.game.army.character.repository;

import org.game.army.character.input.CharacterSearch;
import org.game.army.character.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CharacterRepositoryCustom {

    Page<Character> findAll(CharacterSearch search, Pageable pageable);

}
