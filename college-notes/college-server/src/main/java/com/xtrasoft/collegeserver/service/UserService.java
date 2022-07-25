package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User save(User user);

    /**
     * retrieve all user in the system
     *
     * @return list of user
     */
    List<User> getAll();

    void saveAll(Collection<User> collectionOfUser);

    /**
     * convert excel to corresponding list of user
     *
     * @param file        to convert
     * @param listOfUser to populate
     */
    void convertFileToUser(final MultipartFile file, List<User> listOfUser);

}
