package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.common.ExcelFileUtils;
import com.xtrasoft.collegeserver.models.Serie;
import com.xtrasoft.collegeserver.repository.SerieRepository;
import com.xtrasoft.collegeserver.service.SerieService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.List;
import java.util.function.Consumer;

/**
 * by xtr@soft  on 18/10/2020
 *
 * @author Landry
 **/

@Service
@Transactional
public class SerieServiceImpl implements SerieService {

    private static final Logger LOG = LoggerFactory.getLogger(SerieServiceImpl.class);

    @Autowired
    private SerieRepository serieRepository;

    @Override
    public Serie findByName(String name) {
        Serie result = null;
        try {
            Assert.notNull(name, "name must not null");
            result = serieRepository.findByName(name);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return result;
    }

    @Override
    public void save(Serie serie) {
        try {
            Assert.notNull(serie, "Serie must not be null");
            serieRepository.save(serie);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public List<Serie> getAll() {
        return serieRepository.findAll();
    }

    @Override
    public void saveAll(Collection<Serie> listOfSeries) {
        try {
            if (CollectionUtils.isNotEmpty(listOfSeries)) {
                Assert.noNullElements(listOfSeries, "Any Serie must not be null");
                Consumer<Serie> consumer = serie -> {
                    if (serieRepository.findByName(serie.getName()) != null) {
                        LOG.warn("This serie ({}) already exit in system", serie.getName());
                    } else {
                        serieRepository.save(serie);
                    }
                };
                listOfSeries.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public void convertFileToSerie(final MultipartFile file, List<Serie> listOfSerie) {
        try {
            Assert.notNull(file, "Serie file must not be null");
            final Workbook workbook = ExcelFileUtils.getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row = worksheet.getRow(0);
            Serie serie;
            Cell cell = row.getCell(0);

            if (cell == null && !"name".equals(cell.getStringCellValue())) {
                throw new IllegalArgumentException("Header must be name ");
            }
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                cell = row.getCell(0);

                if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())) {
                    serie = new Serie();
                    serie.setName(cell.getStringCellValue());
                    listOfSerie.add(serie);
                }

            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }


}
