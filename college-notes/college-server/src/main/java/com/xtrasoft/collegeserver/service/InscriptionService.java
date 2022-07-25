package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Classe;
import com.xtrasoft.collegeserver.models.Inscription;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/
public interface InscriptionService {

    /**
     * Create inscription
     *
     * @param inscription to create in the system
     */
    void save(Inscription inscription);

    /**
     * retrieve all inscription in the system
     *
     * @return list of inscription
     */
    List<Inscription> getAll();

    /**
     * save all collection
     * @param collectionOfInscription to save
     */
    void saveAll(Collection<Inscription> collectionOfInscription);

    /**
     * convert excel to corresponding list of inscription
     *
     * @param file              to convert
     * @param listOfInscription to populate
     */
    void convertFileToInscription(final MultipartFile file, List<Inscription> listOfInscription);

    List<Inscription> getByClasse(Classe classe);
}
