package com.xtrasoft.collegeserver.controller;


import com.xtrasoft.collegeserver.models.Student;
import com.xtrasoft.collegeserver.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @GetMapping
    public ModelAndView showRegistrationForm(ModelAndView model) {
        model.addObject("student", new Student());

        model.setViewName("registration");

        return model;
    }

    @PostMapping
    public String registerStudent(@ModelAttribute("student") Student student, BindingResult result) {
        Student existing = studentRepository.findByMatricule(student.getMatricule());
        if (existing != null) {
            result.rejectValue("matricule", null, "There is already an student registered with that matricule");
        }

        if (result.hasErrors()) {
            return "registration";
        }
        System.out.println(student);
        studentRepository.save(student);

        return "redirect:/registration?success";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Student city) {
        System.out.println(city);
        studentRepository.save(city);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> get(@PathVariable(value = "id") Long id) {
        Optional<Student> stutent = studentRepository.findById(id);
        return new ResponseEntity<>(stutent.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Student> get(@PathVariable(value = "name") String name) {
        Student student = studentRepository.findByMatricule(name);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

}
