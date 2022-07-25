package com.xtrasoft.collegeserver.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * by xtr@soft  on 10/10/2020
 *
 * @author Landry
 **/

@Entity
public class Note implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Inscription inscription;

    @ManyToOne
    private Enseignement enseignement;

    @Enumerated(EnumType.STRING)
    private Evaluation evaluation;

    @Max(20)
    @Min(0)
    private Double moy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Double getMoy() {
        return moy;
    }

    public void setMoy(Double moy) {
        this.moy = moy;
    }

    public Enseignement getEnseignement() {
        return enseignement;
    }

    public void setEnseignement(Enseignement enseignement) {
        this.enseignement = enseignement;
    }
}
