package com.xtrasoft.collegeserver.multitenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import static com.xtrasoft.collegeserver.constants.ServerConstant.DEFAULT_TENANT;

@Component
public class TenantSchemaResolver implements CurrentTenantIdentifierResolver {


    @Override
    public String resolveCurrentTenantIdentifier() {
        var tenant = TenantContext.getCurrentTenant();

        if(tenant != null){
            return tenant;
        }
        return DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
