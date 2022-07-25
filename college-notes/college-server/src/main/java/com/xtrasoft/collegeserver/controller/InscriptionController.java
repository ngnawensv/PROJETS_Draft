package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.models.Inscription;
import com.xtrasoft.collegeserver.service.InscriptionService;
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
@RequestMapping("/api/v1/inscription")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<Inscription> listOfInscription = new ArrayList<>(2000);
        inscriptionService.convertFileToInscription(file, listOfInscription);
        inscriptionService.saveAll(listOfInscription);
    }

    @GetMapping("/getAll")
    public List<Inscription> getAll() {
        return inscriptionService.getAll();
    }
}
