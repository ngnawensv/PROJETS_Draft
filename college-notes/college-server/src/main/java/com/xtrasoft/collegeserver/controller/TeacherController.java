package com.xtrasoft.collegeserver.controller;

import com.xtrasoft.collegeserver.models.Teacher;
import com.xtrasoft.collegeserver.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping(value = "/saveAll", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveAll(@RequestParam("file") MultipartFile file) {
        List<Teacher> listOfTeacher = new ArrayList<>(500);
        teacherService.convertFileToTeacher(file, listOfTeacher);
        teacherService.saveAll(listOfTeacher);
    }

    @GetMapping ("/getAll")
    public List<Teacher> getAll() {
        return teacherService.getAll();
    }
}
