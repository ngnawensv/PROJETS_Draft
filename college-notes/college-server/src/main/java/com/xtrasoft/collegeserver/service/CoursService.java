package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Cours;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/
public interface CoursService {

    /**
     * Create cours
     *
     * @param cours to create in the system
     */
    void save(Cours cours);

    /**
     * retrieve all cours in the system
     *
     * @return list of cours
     */
    List<Cours> getAll();

    void saveAll(Collection<Cours> collectionOfCours);

    /**
     * convert excel to corresponding list of cours
     *
     * @param file        to convert
     * @param listOfCours to populate
     */
    void convertFileToCours(final MultipartFile file, List<Cours> listOfCours);
}
