package com.xtrasoft.collegeserver.facade;

import com.xtrasoft.collegeserver.dto.EvaluationDTO;

import java.util.List;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/
public interface NoteFacade {

    List<EvaluationDTO> getNoteEvaluation(String classeId,String enseignementId, String eval);
}