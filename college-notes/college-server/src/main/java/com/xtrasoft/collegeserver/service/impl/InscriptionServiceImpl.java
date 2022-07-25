package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.*;
import com.xtrasoft.collegeserver.repository.ClasseRepository;
import com.xtrasoft.collegeserver.repository.InscriptionRepository;
import com.xtrasoft.collegeserver.repository.StudentRepository;
import com.xtrasoft.collegeserver.service.InscriptionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getCellAsString;
import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getWorkbook;

/**
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/
@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {

    private Logger LOG = LoggerFactory.getLogger(InscriptionServiceImpl.class);

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void save(Inscription inscription) {
        try {
            Assert.notNull(inscription, "Inscription must not be null");
            inscriptionRepository.save(inscription);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Inscription> getAll() {
        try {
            return inscriptionRepository.findAll();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveAll(Collection<Inscription> collectionOfInscription) {
        try {
            if (CollectionUtils.isNotEmpty(collectionOfInscription)) {
                Assert.noNullElements(collectionOfInscription, "Any Inscription must not be null");
                Consumer<Inscription> consumer = inscription -> {
                    if (inscriptionRepository.findInscriptionByMatricule(inscription.getStudent().getMatricule()) != null) {
                        LOG.warn("This inscription for student ({}) in classe {} already exit in system", inscription.getStudent().getMatricule(), inscription.getClasse().getName());
                    } else {
                        var student = inscription.getStudent();
                        student = studentRepository.save(student);
                        inscription.setStudent(student);
                        inscriptionRepository.save(inscription);
                    }
                };
                collectionOfInscription.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void convertFileToInscription(MultipartFile file, List<Inscription> listOfInscription) {
        try {
            Assert.notNull(file, "Inscription file must not be null");
            final Workbook workbook = getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row;
            Inscription inscription;
            Student student;
            valideHeader(worksheet.getRow(0));
            Map<String, Classe> mapOfSerie = getMapOfClasse();
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                final String matricule = getCellAsString(row, 0);
                final String studentName = getCellAsString(row, 1);
                final String classeName = getCellAsString(row, 2);
                final String serieName = getCellAsString(row, 3);
                if (classeName != null && serieName != null && mapOfSerie.get(classeName + serieName) != null) {

                    if (matricule != null && studentName != null && inscriptionRepository.findInscriptionByMatricule(matricule) == null) {
                        student = new Student();
                        student.setMatricule(matricule);
                        student.setName(studentName);

                        inscription = new Inscription();
                        inscription.setStudent(student);
                        inscription.setClasse(mapOfSerie.get(classeName + serieName));
                        listOfInscription.add(inscription);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Inscription> getByClasse(Classe classe) {

        List<Inscription> listOfInscription = null;
        try {
            listOfInscription = inscriptionRepository.findByClasse(classe);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }

        return listOfInscription;
    }

    private Map<String, Classe> getMapOfClasse() {

        final List<Classe> listOfClasse = classeRepository.findAll();
        if (CollectionUtils.isEmpty(listOfClasse)) {
            throw new IllegalArgumentException("Classe must import before inscription");
        }

        Function<Classe, String> keyFunction = classe -> classe.getName() + classe.getSerie().getName();

        return listOfClasse.stream().collect(Collectors.toMap(keyFunction, classe -> classe));
    }

    private void valideHeader(final Row row) {
        Cell cell = row.getCell(0);
        if (cell == null && !"matricule".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("First header column must be matricule ");
        }

        cell = row.getCell(1);
        if (cell == null && !"name".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Second header column must be name ");
        }

        cell = row.getCell(2);
        if (cell == null && !"classe".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("third header column must be classe ");
        }

        cell = row.getCell(3);
        if (cell == null && !"serie".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Fourth header column must be serie ");
        }
    }
}
