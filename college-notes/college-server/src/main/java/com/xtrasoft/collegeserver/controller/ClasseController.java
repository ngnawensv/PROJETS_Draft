package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.models.Classe;
import com.xtrasoft.collegeserver.service.ClasseService;
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
@RequestMapping("/api/v1/classe")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<Classe> listOfClasse = new ArrayList<>(100);
        classeService.convertFileToClasse(file, listOfClasse);
        classeService.saveAll(listOfClasse);
    }

    @GetMapping("/getAll")
    public List<Classe> getAll() {
        return classeService.getAll();
    }
}
