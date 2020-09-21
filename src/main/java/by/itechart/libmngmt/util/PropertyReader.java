package by.itechart.libmngmt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final String PROPERTIES_PATH = "db.properties";
    private static Properties properties = new Properties();
    private final static Logger LOGGER = LoggerFactory.getLogger(ConnectionHelper.class);

    static {
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            LOGGER.error(e.toString());
        }
    }

    private PropertyReader() {
    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
