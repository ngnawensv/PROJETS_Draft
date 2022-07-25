package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Classe;
import com.xtrasoft.collegeserver.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {

    Classe findByNameAndSerie(String name, Serie serie);
}
