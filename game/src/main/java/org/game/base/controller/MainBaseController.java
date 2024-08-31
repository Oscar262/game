package org.game.base.controller;

import org.game.base.service.MainBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Validated
@EnableWebMvc
@RestController
public class MainBaseController {

    @Autowired
    private MainBaseService mainBaseService;



}
