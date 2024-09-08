package org.game.base.controller;

import org.game.base.model.MainBase;
import org.game.base.service.MainBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Validated
@EnableWebMvc
@RestController
public class MainBaseController {

    @Autowired
    private MainBaseService mainBaseService;


    @PostMapping(path = "/base", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public MainBase newMainBase(@RequestPart("main_base") MainBase mainBase,
                               @RequestPart(value = "level_1", required = false) MultipartFile level1,
                               @RequestPart(value = "level_2", required = false) MultipartFile level2,
                               @RequestPart(value = "level_3", required = false) MultipartFile level3,
                               @RequestPart(value = "blazon", required = false) MultipartFile blazon
                               ) {
        return mainBaseService.newMainBase(mainBase, level1, level2, level3, blazon);
    }

    @PutMapping(path = "/base", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public MainBase imageMainBase(@RequestPart(value = "level_1", required = false) MultipartFile level1,
                               @RequestPart(value = "level_2", required = false) MultipartFile level2,
                               @RequestPart(value = "level_3", required = false) MultipartFile level3
                               ) {
        return mainBaseService.mainBaseImage(level1, level2, level3);
    }

}
