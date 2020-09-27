package by.itechart.libmngmt.util;

import by.itechart.libmngmt.repository.impl.BookRepositoryImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionPool {
    private static final ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class.getName());
    private static final String DB_DRIVER = PropertyReader.getProperty("jdbc.driverClassName");
    private static final String DB_URL = PropertyReader.getProperty("jdbc.url");
    private static final String DB_USERNAME = PropertyReader.getProperty("jdbc.username");
    private static final String DB_USERPASSWORD = PropertyReader.getProperty("jdbc.password");
    private static final String UNICODE_PROPERTY = "true";
    private static final String ENCODING = "UTF8";
    private static volatile ConnectionPool instance;

    public static synchronized ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (BookRepositoryImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    static  {
        try {
            comboPooledDataSource.setDriverClass(DB_DRIVER);
            comboPooledDataSource.setJdbcUrl    (DB_URL);
            comboPooledDataSource.setUser       (DB_USERNAME);
            comboPooledDataSource.setPassword   (DB_USERPASSWORD);
            Properties properties = new Properties();
            properties.setProperty ("user", DB_USERNAME);
            properties.setProperty ("password", DB_USERPASSWORD);
            properties.setProperty ("useUnicode", UNICODE_PROPERTY);
            properties.setProperty ("characterEncoding", ENCODING);
            comboPooledDataSource.setProperties(properties);
            comboPooledDataSource.setMaxStatements             (180);
            comboPooledDataSource.setMaxStatementsPerConnection(180);
            comboPooledDataSource.setMinPoolSize               ( 50);
            comboPooledDataSource.setAcquireIncrement          ( 10);
            comboPooledDataSource.setMaxPoolSize               ( 60);
            comboPooledDataSource.setMaxIdleTime               ( 30);
        } catch (PropertyVetoException e) {
            LOGGER.debug("Adding connection properties error.", e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.debug("Getting connection error.", e);
        }
        return connection;
    }
}
