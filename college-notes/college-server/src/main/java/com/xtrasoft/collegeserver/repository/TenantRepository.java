package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * by xtr@soft  on 14/10/2020
 *
 * @author Landry
 **/
@Repository
public interface TenantRepository extends CrudRepository<Tenant,Long> {
    Tenant findByCode(String code);
}
