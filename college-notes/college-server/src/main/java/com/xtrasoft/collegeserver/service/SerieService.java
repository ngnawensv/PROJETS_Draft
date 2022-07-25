package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Serie;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * by xtr@soft  on 18/10/2020
 *
 * @author Landry
 **/
public interface SerieService {

    Serie findByName(String name);
    void save(Serie serie);
    List<Serie> getAll();
    void saveAll(Collection<Serie> collectionOfSerie);
    void convertFileToSerie(final MultipartFile file, List<Serie> listOfSerie);
}
