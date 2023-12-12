package com.tutorcenter.configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.springframework.stereotype.Component;

@Component
public class LogsConnectionFactory {

    private static interface Singleton {
        final LogsConnectionFactory INSTANCE = new LogsConnectionFactory();
    }

    private final DataSource dataSource;

    private String datasourceURL = "jdbc:mysql://d216150.tino.org/vietnamt1_tc?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false";
    private String userName = "vietnamt1_tc";
    private String pass = "abc123456";

    // local docker
    // private String datasourceURL =
    // "jdbc:mysql://host.docker.internal:3306/vietnamt1_tc?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false";
    // private String userName = "root";
    // private String pass = "123456";

    private LogsConnectionFactory() {

        Properties properties = new Properties();
        properties.setProperty("user", userName);
        properties.setProperty("password", pass);

        GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<PoolableConnection>();
        DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                datasourceURL, properties);

        new PoolableConnectionFactory(connectionFactory, pool, null, "SELECT 1", 3, false, false,
                Connection.TRANSACTION_READ_COMMITTED);

        this.dataSource = new PoolingDataSource(pool);
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }

}