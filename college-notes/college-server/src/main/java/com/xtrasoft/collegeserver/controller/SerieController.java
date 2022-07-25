package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.models.Serie;
import com.xtrasoft.collegeserver.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * by xtr@soft  on 18/10/2020
 *
 * @author Landry
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<Serie> listOfSerie = new ArrayList<>(50);
        serieService.convertFileToSerie(file, listOfSerie);
        serieService.saveAll(listOfSerie);
    }

    @GetMapping ("/getAll")
    public List<Serie> getAll() {

        return serieService.getAll();
    }


}
