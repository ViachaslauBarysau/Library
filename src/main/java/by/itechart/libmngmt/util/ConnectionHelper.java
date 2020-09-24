package by.itechart.libmngmt.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    private final static Logger LOGGER = LogManager.getLogger(ConnectionHelper.class.getName());
    private static final String DB_DRIVER = PropertyReader.getProperty("jdbc.driverClassName");
    private static final String DB_URL = PropertyReader.getProperty("jdbc.url");
    private static final String DB_USERNAME = PropertyReader.getProperty("jdbc.username");
    private static final String DB_USERPASSWORD = PropertyReader.getProperty("jdbc.password");

    private ConnectionHelper() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_USERPASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.debug("Getting connection error.", e);
        }
        return connection;
    }
}
