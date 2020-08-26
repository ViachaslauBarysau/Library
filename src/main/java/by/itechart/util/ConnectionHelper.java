package by.itechart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    private static final String DB_DRIVER = PropertyReader.getProperty("jdbc.driverClassName");
    private static final String DB_URL = PropertyReader.getProperty("jdbc.url");
    private static final String DB_USERNAME = PropertyReader.getProperty("jdbc.username");
    private static final String DB_USERPASSWORD = PropertyReader.getProperty("jdbc.password");

    private final static Logger LOGGER = LoggerFactory.getLogger(ConnectionHelper.class);

    private ConnectionHelper() {
    }

    public static Connection getConnection() {

        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_USERPASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e.toString());
        }
        return connection;
    }

}
