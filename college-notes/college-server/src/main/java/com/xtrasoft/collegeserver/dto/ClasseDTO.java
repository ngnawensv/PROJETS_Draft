package com.xtrasoft.collegeserver.dto;

import java.io.Serializable;
import java.util.List;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/
public class ClasseDTO implements Serializable {

    private Long id;
    private String name;
    private List<EnseignementDTO> listOfEnseignement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EnseignementDTO> getListOfEnseignement() {
        return listOfEnseignement;
    }

    public void setListOfEnseignement(List<EnseignementDTO> listOfEnseignement) {
        this.listOfEnseignement = listOfEnseignement;
    }
}
