package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.models.Cours;
import com.xtrasoft.collegeserver.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/cours")
public class CoursController {

    @Autowired
    private CoursService coursService;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<Cours> listOfCours = new ArrayList<>(500);
        coursService.convertFileToCours(file, listOfCours);
        coursService.saveAll(listOfCours);
    }

    @GetMapping("/getAll")
    public List<Cours> getAll() {
        return coursService.getAll();
    }
}
