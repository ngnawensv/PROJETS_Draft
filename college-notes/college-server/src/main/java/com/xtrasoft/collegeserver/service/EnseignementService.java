package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Enseignement;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/
public interface EnseignementService {

    /**
     * retrieve all enseignement in the system
     *
     * @return list of enseignement
     */
    List<Enseignement> getAll();

    /**
     * save all collection
     * @param collectionOfEnseignement to save
     */
    void saveAll(Collection<Enseignement> collectionOfEnseignement);

    /**
     * convert excel to corresponding list of enseignement
     *  @param file              to convert
     * @param listOfEnseignement to populate
     */
    void convertFileToEnseignement(final MultipartFile file, List<Enseignement> listOfEnseignement);

    /**
     * retrieve all teaching for  teacher
     * @param username to find teaching
     * @return all teaching matching the enter teacher
     */
    List<Enseignement> getEnsignementByUsername(String username);

    Enseignement getEnseignementById(Long id);
}
