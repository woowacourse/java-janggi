package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public record DbConfig(String url, String username, String password) {

    private static final String PROPERTIES_FILE = "db.properties";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    public static DbConfig load() {
        Properties properties = new Properties();
        try (InputStream inputStream = DbConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("db.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("db.properties 파일을 읽을 수 없습니다.", e);
        }

        return new DbConfig(
                resolveRequired(properties, URL_KEY),
                resolveRequired(properties, USERNAME_KEY),
                resolveOptional(properties, PASSWORD_KEY)
        );
    }

    private static String resolveRequired(Properties properties, String key) {
        String value = resolveOptional(properties, key);
        if (value.isBlank()) {
            throw new IllegalStateException(key + " 설정이 비어 있습니다.");
        }
        return value;
    }

    private static String resolveOptional(Properties properties, String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        return properties.getProperty(key, "");
    }
}
