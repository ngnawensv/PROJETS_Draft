package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.Enseignement;
import com.xtrasoft.collegeserver.models.Evaluation;
import com.xtrasoft.collegeserver.models.Inscription;
import com.xtrasoft.collegeserver.models.Note;
import com.xtrasoft.collegeserver.repository.NoteRepository;
import com.xtrasoft.collegeserver.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/

@Service
public class NoteServiceImpl implements NoteService {

    private static final Logger LOG = LoggerFactory.getLogger(NoteServiceImpl.class);

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<Note> getByEnsignementAndEvaluation(Enseignement enseignement, Evaluation evaluation) {

        List<Note> listOfNote = null;
        try {
            listOfNote = noteRepository.findByEnseignementAndEvaluation(enseignement, evaluation);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return listOfNote;
    }

    @Override
    public Note getNoteByInscriptionAndEvaluation(Inscription inscription,Evaluation evaluation) {

        try {
            return noteRepository.findByInscriptionAndEvaluation(inscription,evaluation);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Note save(Note note) {
        try {
            return noteRepository.save(note);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }
}
