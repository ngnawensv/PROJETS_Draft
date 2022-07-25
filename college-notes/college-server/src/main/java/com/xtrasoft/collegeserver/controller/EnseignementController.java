package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.dto.ClasseDTO;
import com.xtrasoft.collegeserver.facade.EnseignementFacade;
import com.xtrasoft.collegeserver.models.Enseignement;
import com.xtrasoft.collegeserver.service.EnseignementService;
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
@RequestMapping("/api/v1/enseignement")
public class EnseignementController {

    @Autowired
    private EnseignementService enseignementService;

    @Autowired
    private EnseignementFacade enseignementFacade;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<Enseignement> listOfEnseignement = new ArrayList<>(1000);
        enseignementService.convertFileToEnseignement(file, listOfEnseignement);
        enseignementService.saveAll(listOfEnseignement);
    }

    @GetMapping("/getAll")
    public List<Enseignement> getAll() {
        return enseignementService.getAll();
    }

    @GetMapping("/teacher/{username}")
    public List<ClasseDTO> getEnseignementForTeacher(@PathVariable("username") String username) {

        return enseignementFacade.getEnseignementByUsername(username);
    }

}
