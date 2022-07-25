package com.xtrasoft.collegeserver.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * by xtr@soft  on 10/10/2020
 *
 * @author Landry
 **/

@Entity
public class Inscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Student student;

    @ManyToOne
    private Classe classe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
}
