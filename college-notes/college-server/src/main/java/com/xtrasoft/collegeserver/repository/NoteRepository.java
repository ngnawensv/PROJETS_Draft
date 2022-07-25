package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Enseignement;
import com.xtrasoft.collegeserver.models.Evaluation;
import com.xtrasoft.collegeserver.models.Inscription;
import com.xtrasoft.collegeserver.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/
@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    List<Note> findByEnseignementAndEvaluation(Enseignement enseignement, Evaluation evaluation);

    Note findByInscriptionAndEvaluation(Inscription inscription,Evaluation evaluation);
}
