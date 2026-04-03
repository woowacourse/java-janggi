package janggi.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private final Properties properties;

    public AppConfig() {
        this.properties = readProperties();
    }

    private Properties readProperties() {
        Properties properties = new Properties();

        try(InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException("application.properties 파일이 없습니다.");
            }

            properties.load(inputStream);
        }catch (IOException e) {
            throw new IllegalStateException("application.properties를 읽어오는데 실패했습니다.");
        }

        return properties;
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }
}
