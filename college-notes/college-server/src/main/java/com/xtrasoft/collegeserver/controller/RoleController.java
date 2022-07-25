package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.models.Role;
import com.xtrasoft.collegeserver.service.RoleService;
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
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<Role> listOfRole = new ArrayList<>(50);
        roleService.convertFileToRole(file, listOfRole);
        roleService.saveAll(listOfRole);
    }

    @GetMapping ("/getAll")
    public List<Role> getAll() {
        return roleService.getAll();
    }
}
