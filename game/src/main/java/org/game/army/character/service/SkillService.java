package org.game.army.character.service;

import org.game.army.character.model.Character;
import org.game.army.character.model.Skill;
import org.game.army.character.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;


    public List<Skill> getSkillByCharacterType(Character.BasicType type){
        List<Skill> skills = skillRepository.findAll();
        return skills.stream().filter(skill -> skill.getEnabledForBasicTypes().contains(type)).collect(Collectors.toList());
    }
}
