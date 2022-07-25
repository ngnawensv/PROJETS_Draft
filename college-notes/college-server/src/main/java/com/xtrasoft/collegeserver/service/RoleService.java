package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/
public interface RoleService {

    /**
     * retrieve all role in the system
     *
     * @return list of role
     */
    List<Role> getAll();

    /**
     * save collection of role
     * @param collectionOfRole to save
     */
    void saveAll(Collection<Role> collectionOfRole);

    /**
     * convert excel to corresponding list of role
     *
     * @param file        to convert
     * @param listOfRole to populate
     */
    void convertFileToRole(final MultipartFile file, List<Role> listOfRole);
}
