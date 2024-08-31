package org.game.base.service;

import org.game.base.repository.MainBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainBaseService {

    @Autowired
    private MainBaseRepository mainBaseRepository;
}
