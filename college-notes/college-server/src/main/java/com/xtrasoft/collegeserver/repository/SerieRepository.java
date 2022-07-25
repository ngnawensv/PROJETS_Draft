package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * by xtr@soft  on 18/10/2020
 *
 * @author Landry
 **/

@Repository
public interface SerieRepository extends JpaRepository<Serie,Long> {

    Serie findByName(String name);
}
