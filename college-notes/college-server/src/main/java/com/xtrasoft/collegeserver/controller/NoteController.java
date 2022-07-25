package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.dto.EvaluationDTO;
import com.xtrasoft.collegeserver.facade.NoteFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/note")
public class NoteController {

    @Autowired
    private NoteFacade noteFacade;

    @GetMapping("/evaluation/{classeId}/{enseignementId}/{evaluation}")
    public List<EvaluationDTO> getNoteEvaluation(@PathVariable String classeId,
                                                 @PathVariable String enseignementId,
                                                 @PathVariable String evaluation) {
        return noteFacade.getNoteEvaluation(classeId, enseignementId, evaluation);
    }
}
