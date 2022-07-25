package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Classe;
import com.xtrasoft.collegeserver.models.Cours;
import com.xtrasoft.collegeserver.models.Enseignement;
import com.xtrasoft.collegeserver.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/

@Repository
public interface EnseignementRepository extends JpaRepository<Enseignement,Long> {

    Enseignement findByTeacherAndClasseAndCours(Teacher teacher, Classe classe, Cours cours);
    List<Enseignement> findByTeacher(Teacher teacher);

}
