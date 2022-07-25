package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Enseignement;
import com.xtrasoft.collegeserver.models.Evaluation;
import com.xtrasoft.collegeserver.models.Inscription;
import com.xtrasoft.collegeserver.models.Note;

import java.util.List;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/
public interface NoteService {

    List<Note> getByEnsignementAndEvaluation(Enseignement enseignement, Evaluation evaluation);

    Note getNoteByInscriptionAndEvaluation(Inscription inscription,Evaluation evaluation);

    Note save(Note note);
}
