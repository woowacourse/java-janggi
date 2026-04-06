package janggi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesReader {

    private PropertiesReader() {

    }

    public static Properties read(final String fileName) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final Properties properties = new Properties();
        
        try (final InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
