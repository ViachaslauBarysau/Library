package by.itechart.libmngmt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Provides methods for reading sql script.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseInitializer {
    private static final Logger LOGGER = LogManager.getLogger(DatabaseInitializer.class.getName());
    private static final String CLASSPATH_DB_INIT_SCRIPT_SQL = "src/main/resources/db_init_script.sql";
    private static volatile DatabaseInitializer instance;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    public static synchronized DatabaseInitializer getInstance() {
        DatabaseInitializer localInstance = instance;
        if (localInstance == null) {
            synchronized (DatabaseInitializer.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DatabaseInitializer();
                }
            }
        }
        return localInstance;
    }

    /**
     * Reads script that creates tables and fills it.
     */
    public void createDatabase() {
        try (Reader reader = new BufferedReader(new FileReader(CLASSPATH_DB_INIT_SCRIPT_SQL))) {
            new ScriptRunner(connectionPool.getConnection()).runScript(reader);
        } catch (IOException e) {
            LOGGER.debug("Initializing database error.", e);
        }
    }
}
