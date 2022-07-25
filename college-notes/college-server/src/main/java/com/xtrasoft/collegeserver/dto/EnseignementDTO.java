package com.xtrasoft.collegeserver.dto;

import java.io.Serializable;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/
public class EnseignementDTO  implements Serializable {

    private Long id;
    private String cours;
    private int totalEffectif;
    private int evalEffectif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public int getTotalEffectif() {
        return totalEffectif;
    }

    public void setTotalEffectif(int totalEffectif) {
        this.totalEffectif = totalEffectif;
    }

    public int getEvalEffectif() {
        return evalEffectif;
    }

    public void setEvalEffectif(int evalEffectif) {
        this.evalEffectif = evalEffectif;
    }
}
