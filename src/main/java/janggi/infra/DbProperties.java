package janggi.infra;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbProperties {

    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";

    private final Properties properties;

    public DbProperties() {
        this.properties = readProperties(DEFAULT_PROPERTIES_FILE);
    }

    public DbProperties(String fileName) {
        this.properties = readProperties(fileName);
    }

    private Properties readProperties(String filename) {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(filename)) {

            if (inputStream == null) {
                throw new IllegalStateException("application.properties 파일이 없습니다.");
            }

            properties.load(inputStream);
        } catch (IOException e) {
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
