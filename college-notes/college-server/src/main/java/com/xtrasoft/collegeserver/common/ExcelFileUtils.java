package com.xtrasoft.collegeserver.common;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * by xtr@soft  on 18/10/2020
 *
 * @author Landry
 **/
public class ExcelFileUtils {

    public static Workbook getWorkbook(final MultipartFile file) throws IOException {
        String extensionFile = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extensionFile.equals("xlsx")) {
            return new XSSFWorkbook(file.getInputStream());
        } else if (extensionFile.endsWith("xls")) {
            return new HSSFWorkbook(file.getInputStream());
        }

        throw new IllegalArgumentException("The specified file is not Excel file");
    }

    public static String getCellAsString(final Row row, final int cellNumber) {
        String result = null;
        final Cell cell = row.getCell(cellNumber);
        if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())) {
            result = cell.getStringCellValue().strip();
        }
        return result;
    }
}
