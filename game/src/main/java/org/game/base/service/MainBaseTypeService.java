package org.game.base.service;

import org.game.base.model.MainBaseType;
import org.game.base.repository.MainBaseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainBaseTypeService {

    @Autowired
    private MainBaseTypeRepository mainBaseTypeRepository;

    public MainBaseType getById(Long id){
        return mainBaseTypeRepository.findById(id).get();
    }
}
