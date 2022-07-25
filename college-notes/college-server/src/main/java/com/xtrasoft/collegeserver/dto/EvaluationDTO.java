package com.xtrasoft.collegeserver.dto;

import java.io.Serializable;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/
public class EvaluationDTO implements Serializable {

    private Long noteId;
    private String student;
    private String matricule;
    private Double moy;

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Double getMoy() {
        return moy;
    }

    public void setMoy(Double moy) {
        this.moy = moy;
    }
}
