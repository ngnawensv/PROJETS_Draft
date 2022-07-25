package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.Classe;
import com.xtrasoft.collegeserver.models.Level;
import com.xtrasoft.collegeserver.models.Serie;
import com.xtrasoft.collegeserver.repository.ClasseRepository;
import com.xtrasoft.collegeserver.repository.SerieRepository;
import com.xtrasoft.collegeserver.service.ClasseService;
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
 * @author
 **/

@Service
public class ClasseServiceImpl implements ClasseService {

    private Logger LOG = LoggerFactory.getLogger(ClasseServiceImpl.class);

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public void save(Classe classe) {
        try {
            Assert.notNull(classe, "Classe must not be null");
            classeRepository.save(classe);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Classe> getAll() {
        try {
            return classeRepository.findAll();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveAll(Collection<Classe> listOfClasses) {
        try {
            if (CollectionUtils.isNotEmpty(listOfClasses)) {
                Assert.noNullElements(listOfClasses, "Any Classe must not be null");
                Consumer<Classe> consumer = classe -> {
                    if (classeRepository.findByNameAndSerie(classe.getName(), classe.getSerie()) != null) {
                        LOG.warn("This classe ({}) already exit in system", classe.getName());
                    } else {
                        classeRepository.save(classe);
                    }
                };
                listOfClasses.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void convertFileToClasse(MultipartFile file, List<Classe> listOfClasse) {
        try {
            Assert.notNull(file, "Classe file must not be null");
            final Workbook workbook = getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row;
            Classe classe;
            valideHeader(worksheet.getRow(0));
            Map<String, Serie> mapOfSerie = getMapOfSerie();
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                String serieName = getCellAsString(row, 1);
                if (serieName != null && mapOfSerie.get(serieName) != null) {
                    final String classeName = getCellAsString(row, 0);
                    final String levelAsString = getCellAsString(row, 2);
                    if (classeName != null && levelAsString != null) {
                        classe = new Classe();
                        classe.setName(classeName);
                        classe.setSerie(mapOfSerie.get(serieName));
                        classe.setLevel(Level.valueOf(levelAsString));
                        listOfClasse.add(classe);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public Classe getClasseById(Long id) {
        try {
            return classeRepository.getOne(id);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
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
