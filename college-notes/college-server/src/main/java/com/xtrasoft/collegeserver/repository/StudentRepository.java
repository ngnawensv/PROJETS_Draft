package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * by xtr@soft  on 10/10/2020
 *
 * @author Landry
 **/

@Repository
public interface StudentRepository extends CrudRepository<Student,Long> {

    Student findByMatricule(String matricule);
}
