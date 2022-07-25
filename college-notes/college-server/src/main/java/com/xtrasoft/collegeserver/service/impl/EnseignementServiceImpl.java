package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.*;
import com.xtrasoft.collegeserver.repository.*;
import com.xtrasoft.collegeserver.service.EnseignementService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getCellAsString;
import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getWorkbook;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/

@Service
public class EnseignementServiceImpl implements EnseignementService {

    private Logger LOG = LoggerFactory.getLogger(EnseignementServiceImpl.class);

    @Autowired
    private EnseignementRepository enseignementRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public List<Enseignement> getAll() {
        try {
            return enseignementRepository.findAll();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveAll(Collection<Enseignement> collectionOfEnseignement) {
        try {
            if (CollectionUtils.isNotEmpty(collectionOfEnseignement)) {
                Assert.noNullElements(collectionOfEnseignement, "Any Enseignement must not be null");
                Consumer<Enseignement> consumer = teacher -> {
                    if (enseignementRepository.findByTeacherAndClasseAndCours(teacher.getTeacher(), teacher.getClasse(), teacher.getCours()) == null) {
                        enseignementRepository.save(teacher);
                    }
                };
                collectionOfEnseignement.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void convertFileToEnseignement(MultipartFile file, List<Enseignement> listOfEnseignement) {
        try {
            Assert.notNull(file, "Enseignement file must not be null");
            final Workbook workbook = getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row;
            Enseignement enseignement;
            valideHeader(worksheet.getRow(0));
            Map<String, Serie> mapOfSerie = getMapOfSerie();
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                String teacherName = getCellAsString(row, 0);
                String classeName = getCellAsString(row, 1);
                String coursName = getCellAsString(row, 2);
                String serieName = getCellAsString(row, 3);
                Classe classe = getClasse(classeName, mapOfSerie.get(serieName));
                Cours cours = getCours(coursName, classe);
                Teacher teacher = getTeacherByUsername(teacherName);
                if (cours != null && teacher != null) {
                    enseignement = new Enseignement();
                    enseignement.setTeacher(teacher);
                    enseignement.setClasse(classe);
                    enseignement.setCours(cours);
                    listOfEnseignement.add(enseignement);
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Enseignement> getEnsignementByUsername(final String username) {

        List<Enseignement> listOfEnseignement = null;
        try {
            Assert.notNull(username, "username must not be null");
            var teacher = getTeacherByUsername(username);
            if (teacher != null) {
                listOfEnseignement = enseignementRepository.findByTeacher(teacher);
            }

        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return listOfEnseignement;
    }

    @Override
    public Enseignement getEnseignementById(Long id) {
        try {
            return enseignementRepository.getOne(id);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    private Teacher getTeacherByUsername(String teacherName) {
        Teacher result = null;
        if (teacherName != null) {
            result = teacherRepository.findByUserName(teacherName);
        }
        return result;
    }

    private Cours getCours(String coursName, Classe classe) {
        Cours result = null;
        if (coursName != null && classe != null) {
            result = coursRepository.findByNameAndSerieAndLevel(coursName, classe.getSerie(), classe.getLevel());
        }
        return result;
    }

    private Classe getClasse(String classeName, Serie serie) {
        Classe classe = null;
        if (classeName != null && serie != null) {
            classe = classeRepository.findByNameAndSerie(classeName, serie);
        }
        return classe;
    }

    private Map<String, Serie> getMapOfSerie() {

        final List<Serie> listOfSerie = serieRepository.findAll();
        if (CollectionUtils.isEmpty(listOfSerie)) {
            throw new IllegalArgumentException("Serie must import before classe");
        }

        return listOfSerie.stream().collect(Collectors.toMap(Serie::getName, serie -> serie));
    }

    private void valideHeader(final Row row) {
        Cell cell = row.getCell(0);
        if (cell == null && !"teacher".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("First header column must be teacher ");
        }

        cell = row.getCell(1);
        if (cell == null && !"classe".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Second header column must be classe ");
        }

        cell = row.getCell(2);
        if (cell == null && !"cours".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("third header column must be cours ");
        }

        cell = row.getCell(3);
        if (cell == null && !"serie".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Fourth header column must be serie ");
        }
    }
}
