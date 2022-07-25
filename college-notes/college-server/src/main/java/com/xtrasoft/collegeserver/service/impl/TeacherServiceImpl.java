package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.Teacher;
import com.xtrasoft.collegeserver.models.User;
import com.xtrasoft.collegeserver.repository.TeacherRepository;
import com.xtrasoft.collegeserver.repository.UserRepository;
import com.xtrasoft.collegeserver.service.TeacherService;
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
import java.util.List;
import java.util.function.Consumer;

import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getCellAsString;
import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getWorkbook;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/
@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public void saveAll(Collection<Teacher> listOfSeries) {
        try {
            if (CollectionUtils.isNotEmpty(listOfSeries)) {
                Assert.noNullElements(listOfSeries, "Any Teacher must not be null");
                Consumer<Teacher> consumer = teacher -> {
                    if (teacherRepository.findByUser(teacher.getUser()) == null) {
                        teacherRepository.save(teacher);
                    } else {
                        LOG.warn("This teacher {} already exit in system", teacher.getFullName());
                    }
                };
                listOfSeries.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void convertFileToTeacher(MultipartFile file, List<Teacher> listOfTeacher) {
        try {
            Assert.notNull(file, "Teacher file must not be null");
            final Workbook workbook = getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row;
            Teacher teacher;
            valideHeader(worksheet.getRow(0));
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                String teacherName = getCellAsString(row, 0);
                String userName = getCellAsString(row, 1);
                User user = getUser(userName);
                if (teacherName != null && user != null) {
                    teacher = new Teacher();
                    teacher.setFullName(teacherName);
                    teacher.setUser(user);
                    listOfTeacher.add(teacher);
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private User getUser(String userName) {
        User result = null;
        try {
            if (userName != null) {
                result = userRepository.findByUserName(userName);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }

        return result;
    }

    private void valideHeader(final Row row) {
        Cell cell = row.getCell(0);
        if (cell == null && !"name".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("First header column must be name ");
        }

        cell = row.getCell(1);
        if (cell == null && !"username".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Second header column must be username ");
        }
    }
}
