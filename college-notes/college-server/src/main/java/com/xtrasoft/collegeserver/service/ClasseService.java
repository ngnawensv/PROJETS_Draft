package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Classe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/
public interface ClasseService {
    /**
     * Create classe
     *
     * @param classe to create in the system
     */
    void save(Classe classe);

    /**
     * retrieve all classe in the system
     *
     * @return list of classe
     */
    List<Classe> getAll();

    void saveAll(Collection<Classe> collectionOfClasse);

    /**
     * convert excel to corresponding list of classe
     *
     * @param file         to convert
     * @param listOfClasse to populate
     */
    void convertFileToClasse(final MultipartFile file, List<Classe> listOfClasse);

    Classe getClasseById(Long id);
}
