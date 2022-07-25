package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
