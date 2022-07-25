package com.xtrasoft.collegeserver.facade.impl;

import com.xtrasoft.collegeserver.dto.EvaluationDTO;
import com.xtrasoft.collegeserver.facade.NoteFacade;
import com.xtrasoft.collegeserver.models.*;
import com.xtrasoft.collegeserver.service.ClasseService;
import com.xtrasoft.collegeserver.service.EnseignementService;
import com.xtrasoft.collegeserver.service.InscriptionService;
import com.xtrasoft.collegeserver.service.NoteService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/

@Component
public class NoteFacadeImpl implements NoteFacade {

    public static final Logger LOG = LoggerFactory.getLogger(NoteFacadeImpl.class);

    @Autowired
    private ClasseService classeService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private EnseignementService enseignementService;

    @Autowired
    private NoteService noteService;

    @Override
    public List<EvaluationDTO> getNoteEvaluation(String classeId, String enseignementId, String eval) {
        final Classe classe = getClasse(classeId);
        final Enseignement enseignement = getEnseignementById(enseignementId);
        final List<EvaluationDTO> listOfEvaluationDTO = new ArrayList<>(200);
        final Evaluation evalEnum = Evaluation.valueOf(eval);

        if (classe != null && enseignement != null) {

            Consumer<Inscription> consumer = inscription -> {
                Note note = noteService.getNoteByInscriptionAndEvaluation(inscription, evalEnum);
                if (note == null) {
                    note = new Note();
                    note.setEnseignement(enseignement);
                    note.setInscription(inscription);
                    note = noteService.save(note);
                }

                EvaluationDTO evaluationDTO = new EvaluationDTO();
                Student student = inscription.getStudent();
                evaluationDTO.setMatricule(student.getMatricule());
                evaluationDTO.setStudent(student.getName());
                evaluationDTO.setMoy(note.getMoy());
                listOfEvaluationDTO.add(evaluationDTO);
            };

            CollectionUtils.emptyIfNull(inscriptionService.getByClasse(classe)).forEach(consumer);

        }
        return listOfEvaluationDTO;
    }

    private Enseignement getEnseignementById(String enseignementId) {

        Enseignement result = null;
        try {
            if (StringUtils.isNotBlank(enseignementId)) {
                result = enseignementService.getEnseignementById(Long.valueOf(enseignementId));
            }
        } catch (NumberFormatException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        return result;
    }

    private Classe getClasse(String classeId) {
        Classe result = null;
        try {
            if (StringUtils.isNotBlank(classeId)) {
                result = classeService.getClasseById(Long.valueOf(classeId));
            }
        } catch (NumberFormatException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        return result;
    }
}
