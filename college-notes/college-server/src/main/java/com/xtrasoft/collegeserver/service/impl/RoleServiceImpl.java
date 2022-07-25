package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.Role;
import com.xtrasoft.collegeserver.repository.RoleRepository;
import com.xtrasoft.collegeserver.service.RoleService;
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
import java.util.function.Consumer;

import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getCellAsString;
import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getWorkbook;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/
@Service
public class RoleServiceImpl implements RoleService {

    private Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        try {
            return roleRepository.findAll();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveAll(Collection<Role> collectionOfRole) {
        try {
            if (CollectionUtils.isNotEmpty(collectionOfRole)) {
                Assert.noNullElements(collectionOfRole, "Any Role must not be null");
                Consumer<Role> consumer = role -> {
                    if (roleRepository.findByName(role.getName()) != null) {
                        LOG.warn("This role ({}) already exit in system", role.getName());
                    } else {
                        roleRepository.save(role);
                    }
                };
                collectionOfRole.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void convertFileToRole(MultipartFile file, List<Role> listOfRole) {
        try {
            Assert.notNull(file, "Role file must not be null");
            final Workbook workbook = getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row;
            Role role;
            valideHeader(worksheet.getRow(0));
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                String roleName = getCellAsString(row, 0);
                if (roleName != null) {
                    role = new Role();
                    role.setName(roleName);
                    listOfRole.add(role);
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void valideHeader(final Row row) {
        Cell cell = row.getCell(0);
        if (cell == null && !"name".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Header column must be name ");
        }
    }
}
