package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Cours;
import com.xtrasoft.collegeserver.models.Level;
import com.xtrasoft.collegeserver.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    Cours findByNameAndSerieAndLevel(String name, Serie serie, Level level);
}
