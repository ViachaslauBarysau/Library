package by.itechart.libmngmt.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private final static Logger logger = LogManager.getLogger(PropertyReader.class.getName());
    private static final String PROPERTIES_PATH = "db.properties";
    private static Properties properties = new Properties();

    static {
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            logger.debug("Reading properties error!", e);
        }
    }

    private PropertyReader() {
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
