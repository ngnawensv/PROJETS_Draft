package com.xtrasoft.collegeserver.facade;

import com.xtrasoft.collegeserver.dto.ClasseDTO;

import java.util.List;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/
public interface EnseignementFacade {

    List<ClasseDTO> getEnseignementByUsername(String username);
}
