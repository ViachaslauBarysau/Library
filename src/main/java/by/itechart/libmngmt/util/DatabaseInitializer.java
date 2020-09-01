package by.itechart.libmngmt.util;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static by.itechart.libmngmt.util.ConnectionHelper.getConnection;

public class DatabaseInitializer {

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseInitializer.class);
    private static final String CLASSPATH_DB_INIT_SCRIPT_SQL = "src/main/resources/db_init_script.sql";

    public DatabaseInitializer() {
    }

    public static void createDatabase() {
        try (Reader reader = new BufferedReader(new FileReader(CLASSPATH_DB_INIT_SCRIPT_SQL))) {
            new ScriptRunner(getConnection()).runScript(reader);
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }
}
