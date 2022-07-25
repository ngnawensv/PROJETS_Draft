package com.xtrasoft.collegeserver.service;

import com.xtrasoft.collegeserver.models.Teacher;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/
public interface TeacherService {

    /**
     * retrieve all teacher in the system
     *
     * @return list of teacher
     */
    List<Teacher> getAll();

    /**
     * save collection of teacher
     *
     * @param collectionOfTeacher to save
     */
    void saveAll(Collection<Teacher> collectionOfTeacher);

    /**
     * convert excel to corresponding list of teacher
     *
     * @param file          to convert
     * @param listOfTeacher to populate
     */
    void convertFileToTeacher(final MultipartFile file, List<Teacher> listOfTeacher);
}
