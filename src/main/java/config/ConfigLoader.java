package config;

import exception.InfraErrorMessage;
import exception.custom.ConfigLoadException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties load() {
        Properties properties = new Properties();
        try (InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is == null) {
                throw new ConfigLoadException(InfraErrorMessage.CONFIG_FILE_NOT_FOUND_ERROR.getMessage());
            }

            properties.load(is);
        } catch (Exception e) {
            throw new ConfigLoadException();
        }

        return properties;
    }
}
