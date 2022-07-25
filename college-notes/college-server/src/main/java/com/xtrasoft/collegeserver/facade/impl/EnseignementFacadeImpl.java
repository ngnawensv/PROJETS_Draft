package com.xtrasoft.collegeserver.facade.impl;

import com.xtrasoft.collegeserver.dto.ClasseDTO;
import com.xtrasoft.collegeserver.dto.EnseignementDTO;
import com.xtrasoft.collegeserver.facade.EnseignementFacade;
import com.xtrasoft.collegeserver.models.*;
import com.xtrasoft.collegeserver.service.EnseignementService;
import com.xtrasoft.collegeserver.service.InscriptionService;
import com.xtrasoft.collegeserver.service.NoteService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * by xtr@soft  on 24/10/2020
 *
 * @author Landry
 **/

@Component
public class EnseignementFacadeImpl implements EnseignementFacade {

    @Autowired
    private EnseignementService enseignementService;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private NoteService noteService;

    @Override
    public List<ClasseDTO> getEnseignementByUsername(final String username) {

        final List<ClasseDTO> listOfClasseDTO = new ArrayList<>(1000);

        final BiConsumer<Classe, List<Enseignement>> biConsumer = (classe, listOfEnseignement) -> {
            ClasseDTO classeDTO = new ClasseDTO();
            classeDTO.setId(classe.getId());
            classeDTO.setName(classe.getName());
            List<Inscription> listOfInscription = inscriptionService.getByClasse(classe);
            int effecttif = listOfInscription != null ? listOfInscription.size() : 0;
            classeDTO.setListOfEnseignement(convertEnseignement(listOfEnseignement, effecttif));
            listOfClasseDTO.add(classeDTO);
        };
        CollectionUtils.emptyIfNull(enseignementService.getEnsignementByUsername(username)).
                stream().collect(Collectors.groupingBy(Enseignement::getClasse)).forEach(biConsumer);
        return listOfClasseDTO;
    }

    private List<EnseignementDTO> convertEnseignement(final List<Enseignement> listOfEnseignement, int effecttif) {

        final Function<Enseignement, EnseignementDTO> function = enseignement -> {
            final EnseignementDTO enseignementDTO = new EnseignementDTO();
            enseignementDTO.setCours(enseignement.getCours().getName());
            enseignementDTO.setId(enseignement.getId());
            enseignementDTO.setTotalEffectif(effecttif);
            List<Note> listOfNote = noteService.getByEnsignementAndEvaluation(enseignement, Evaluation.I);
            enseignementDTO.setEvalEffectif(listOfNote != null ? listOfNote.size() : 0);

            return enseignementDTO;
        };

        return CollectionUtils.emptyIfNull(listOfEnseignement).stream().map(function).collect(Collectors.toList());
    }


}
