package org.game.base.controller;

import org.game.base.model.MainBase;
import org.game.base.service.MainBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Validated
@EnableWebMvc
@RestController
public class MainBaseController {

    @Autowired
    private MainBaseService mainBaseService;


    @PostMapping("/base")
    public MainBase newMinBase(@RequestParam MainBase mainBase,
                               @RequestParam(value = "level_1", required = false) MultipartFile level1,
                               @RequestParam(value = "level_2", required = false) MultipartFile level2,
                               @RequestParam(value = "level_3", required = false) MultipartFile level3,
                               @RequestParam(value = "blazon", required = false) MultipartFile blazon
                               ) {
        return mainBaseService.newMainBase(mainBase, level1, level2, level3, blazon);
    }

}
