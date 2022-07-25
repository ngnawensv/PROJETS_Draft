package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Classe;
import com.xtrasoft.collegeserver.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    @Query("FROM Inscription AS i LEFT JOIN i.student AS s WHERE s.matricule = ?1")
    Inscription findInscriptionByMatricule(String matricule);

    List<Inscription> findByClasse(Classe classe);
}
