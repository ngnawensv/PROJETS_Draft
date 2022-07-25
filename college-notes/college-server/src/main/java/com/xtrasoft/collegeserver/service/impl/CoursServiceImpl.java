package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.Cours;
import com.xtrasoft.collegeserver.models.Level;
import com.xtrasoft.collegeserver.models.Serie;
import com.xtrasoft.collegeserver.repository.CoursRepository;
import com.xtrasoft.collegeserver.repository.SerieRepository;
import com.xtrasoft.collegeserver.service.CoursService;
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
 * by xtr@soft  on 22/10/2020
 *
 * @author Landry
 **/
@Service
public class CoursServiceImpl implements CoursService {


    private Logger LOG = LoggerFactory.getLogger(CoursServiceImpl.class);

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public void save(Cours cours) {
        try {
            Assert.notNull(cours, "Cours must not be null");
            coursRepository.save(cours);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Cours> getAll() {
        try {
            return coursRepository.findAll();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveAll(Collection<Cours> collectionOfCours) {
        try {
            if (CollectionUtils.isNotEmpty(collectionOfCours)) {
                Assert.noNullElements(collectionOfCours, "Any Cours must not be null");
                Consumer<Cours> consumer = cours -> {
                    if (coursRepository.findByNameAndSerieAndLevel(cours.getName(), cours.getSerie(), cours.getLevel()) != null) {
                        LOG.warn("This cours ({}) already exit in system", cours.getName());
                    } else {
                        coursRepository.save(cours);
                    }
                };
                collectionOfCours.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void convertFileToCours(MultipartFile file, List<Cours> listOfCours) {
        try {
            Assert.notNull(file, "Cours file must not be null");
            final Workbook workbook = getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row;
            Cours cours;
            valideHeader(worksheet.getRow(0));
            Map<String, Serie> mapOfSerie = getMapOfSerie();
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                String serieName = getCellAsString(row, 1);
                if (serieName != null && mapOfSerie.get(serieName) != null) {
                    final String classeName = getCellAsString(row, 0);
                    final String levelAsString = getCellAsString(row, 2);
                    if (classeName != null && levelAsString != null) {
                        cours = new Cours();
                        cours.setName(classeName);
                        cours.setSerie(mapOfSerie.get(serieName));
                        cours.setLevel(Level.valueOf(levelAsString));
                        listOfCours.add(cours);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
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
        if (cell == null && !"name".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("First header column must be name ");
        }

        cell = row.getCell(1);
        if (cell == null && !"level".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Second header column must be level ");
        }

        cell = row.getCell(2);
        if (cell == null && !"serie".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Second header column must be serie ");
        }
    }

}
