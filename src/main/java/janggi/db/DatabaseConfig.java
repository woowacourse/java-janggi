package janggi.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private final String url;
    private final String username;
    private final String password;

    private DatabaseConfig(final String url, final String username, final String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DatabaseConfig load() {
        try (InputStream input = DatabaseConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            return new DatabaseConfig(
                    properties.getProperty("database.url"),
                    properties.getProperty("database.username"),
                    properties.getProperty("database.password")
            );
        } catch (IOException e) {
            throw new RuntimeException("application.properties 파일을 읽을 수 없습니다.");
        }
    }

    public String getConnectionUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
