package com.xtrasoft.collegeserver.multitenant;

import com.xtrasoft.collegeserver.constants.ServerConstant;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

    private static Logger LOG = LoggerFactory.getLogger(TenantConnectionProvider.class);

    private DataSource dataSource;

    public TenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        LOG.info("Get connection for tenant {}", tenantIdentifier);
        final Connection connection = getAnyConnection();
        connection.createStatement().execute("USE " + tenantIdentifier);

        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        LOG.info("Get connection for tenant {}", ServerConstant.DEFAULT_TENANT);
        connection.createStatement().execute("USE " + ServerConstant.DEFAULT_TENANT);
        releaseAnyConnection(connection);

    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
