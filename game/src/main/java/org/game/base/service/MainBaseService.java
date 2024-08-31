package org.game.base.service;

import org.game.auth.model.User;
import org.game.auth.service.UserService;
import org.game.base.model.MainBase;
import org.game.base.repository.MainBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MainBaseService {

    @Autowired
    private MainBaseRepository mainBaseRepository;
    @Autowired
    private UserService userService;



    public MainBase newMainBase(MainBase mainBase, MultipartFile level1, MultipartFile level2, MultipartFile level3, MultipartFile blazon) {
        User user = userService.getUser();
        Map<Long, byte[]> map = new HashMap<>();

        byte[] level1Bytes = null;
        if (level1 != null){
            try {
                level1Bytes = level1.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        map.put(2L, level1Bytes);
        byte[] level2Bytes = null;
        if (level1 != null){
            try {
                level2Bytes = level2.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        map.put(1L, level2Bytes);
        byte[] level3Bytes = null;
        if (level1 != null){
            try {
                level3Bytes = level3.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        map.put(3L, level3Bytes);
        mainBase.setLevel(0L);
        mainBase.setImage(map);

        byte[] blazonBytes = null;
        if (blazon != null){
            try {
                blazonBytes = blazon.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        mainBase.setBlazon(blazonBytes);

        MainBase base = mainBaseRepository.save(mainBase);
        user.setMainBase(base);
        userService.save(user);

        return base;
    }
}
