package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.models.*;
import com.xtrasoft.collegeserver.repository.RoleRepository;
import com.xtrasoft.collegeserver.repository.UserRepository;
import com.xtrasoft.collegeserver.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getCellAsString;
import static com.xtrasoft.collegeserver.common.ExcelFileUtils.getWorkbook;


@Service
public class UserServiceImpl implements UserService {

    public static final String ROLE_SPARATOR = "\\|";
    private Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired

    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void saveAll(Collection<User> collectionOfUser) {
        try {
            if (CollectionUtils.isNotEmpty(collectionOfUser)) {
                Assert.noNullElements(collectionOfUser, "Any User must not be null");
                Consumer<User> consumer = user -> {
                    if (userRepository.findByUserName(user.getUserName()) != null) {
                        LOG.warn("This user ({}) already exit in system", user.getUserName());
                    } else {
                        userRepository.save(user);
                    }
                };
                collectionOfUser.forEach(consumer);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void convertFileToUser(MultipartFile file, List<User> listOfUser) {
        try {
            Assert.notNull(file, "User file must not be null");
            final Workbook workbook = getWorkbook(file);
            final Sheet worksheet = workbook.getSheetAt(0);
            Row row;
            User user;
            valideHeader(worksheet.getRow(0));
            Map<String, Role> mapOfRole = getMapOfRole();
            for (int index = 1; index < worksheet.getPhysicalNumberOfRows(); index++) {
                row = worksheet.getRow(index);
                String username = getCellAsString(row, 0);
                String password = getCellAsString(row, 1);
                String rolesAsString = getCellAsString(row, 2);
                if (username != null && password != null) {
                    List<Role> listOfRoles = getUserRoles(rolesAsString, mapOfRole);
                    if (CollectionUtils.isNotEmpty(listOfRoles)) {
                        user = new User();
                        user.setUserName(username);
                        user.setPassword(passwordEncoder.encode(password));
                        user.setRoles(listOfRoles);
                        listOfUser.add(user);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private List<Role> getUserRoles(final String rolesAsString, final Map<String, Role> mapOfRole) {

        if (StringUtils.isNotBlank(rolesAsString)) {
            String[] vectorOfRole = rolesAsString.split(ROLE_SPARATOR);
            return Arrays.stream(vectorOfRole).map(role -> mapOfRole.get(role)).filter(Objects::nonNull).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    private Map<String, Role> getMapOfRole() {

        final List<Role> listOfRole = roleRepository.findAll();
        if (CollectionUtils.isEmpty(listOfRole)) {
            throw new IllegalArgumentException("Role must import before classe");
        }

        return listOfRole.stream().collect(Collectors.toMap(Role::getName, role -> role));
    }

    private void valideHeader(final Row row) {
        Cell cell = row.getCell(0);
        if (cell == null && !"username".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("First header column must be username ");
        }

        cell = row.getCell(1);
        if (cell == null && !"password".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("Second header column must be password ");
        }

        cell = row.getCell(2);
        if (cell == null && !"roles".equals(cell.getStringCellValue())) {
            throw new IllegalArgumentException("third header column must be roles ");
        }
    }

}
