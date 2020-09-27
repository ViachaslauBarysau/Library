package by.itechart.libmngmt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
