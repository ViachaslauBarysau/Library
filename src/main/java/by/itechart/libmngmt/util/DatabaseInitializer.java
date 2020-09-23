package by.itechart.libmngmt.util;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static by.itechart.libmngmt.util.ConnectionHelper.getConnection;

public class DatabaseInitializer {
    private final static Logger logger = LogManager.getLogger(DatabaseInitializer.class.getName());
    private static final String CLASSPATH_DB_INIT_SCRIPT_SQL = "src/main/resources/db_init_script.sql";

    public DatabaseInitializer() {
    }

    public static void createDatabase() {
        try (Reader reader = new BufferedReader(new FileReader(CLASSPATH_DB_INIT_SCRIPT_SQL))) {
            new ScriptRunner(getConnection()).runScript(reader);
        } catch (IOException e) {
            logger.debug("Initializing database error!", e);
        }
    }
}
