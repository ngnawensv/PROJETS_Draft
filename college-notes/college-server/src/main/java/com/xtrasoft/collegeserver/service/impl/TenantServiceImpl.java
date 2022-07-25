package com.xtrasoft.collegeserver.service.impl;

import com.xtrasoft.collegeserver.repository.TenantRepository;
import com.xtrasoft.collegeserver.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * by xtr@soft  on 14/10/2020
 *
 * @author
 **/
@Service
public class TenantServiceImpl implements TenantService {

    private static Logger LOG = LoggerFactory.getLogger(TenantServiceImpl.class);

    private TenantRepository tenantRepository;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public boolean tenantExist(String code) {
        boolean result = false;
        try {

            if (tenantRepository.findByCode(code) != null) {
                result = true;
            }
        } catch (Exception ex) {
            LOG.error("Tenant {} not find!", code);
        }

        return result;
    }
}
