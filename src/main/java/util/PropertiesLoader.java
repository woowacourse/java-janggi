package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static String getProperty(String value) {
        Properties properties = loadProperties();
        return properties.getProperty(value);
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = PropertiesLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    ;
}