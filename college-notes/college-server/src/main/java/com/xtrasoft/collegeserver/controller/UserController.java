package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.models.User;
import com.xtrasoft.collegeserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<User> listOfUser = new ArrayList<>(200);
        userService.convertFileToUser(file, listOfUser);
        userService.saveAll(listOfUser);
    }

    @GetMapping ("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }
}
