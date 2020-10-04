package by.itechart.libmngmt.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Provides method for getting properties from property file.
 */
public class PropertyReader {
    private static final Logger LOGGER = LogManager.getLogger(PropertyReader.class.getName());
    private static final String PROPERTIES_PATH = "db.properties";
    private static volatile Properties properties = new Properties();

    static {
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            LOGGER.debug("Reading properties error.", e);
        }
    }

    /**
     * Creates independent of system file upload path, creates folders
     * and assigns this folder path to the system property.
     */
    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
