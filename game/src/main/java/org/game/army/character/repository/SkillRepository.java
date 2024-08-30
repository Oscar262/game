package org.game.army.character.repository;

import org.game.army.character.model.Character;
import org.game.army.character.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

}
