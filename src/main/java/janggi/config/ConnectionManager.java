package janggi.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private final String host;
    private final String port;
    private final String databaseName;
    private final String user;
    private final String password;

    public ConnectionManager() {
        Properties properties = loadProperties();
        this.host = properties.getProperty("db.host");
        this.port = properties.getProperty("db.port");
        this.databaseName = properties.getProperty("db.name");
        this.user = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");
    }

    public Connection getServerConnection() throws SQLException {
        return DriverManager.getConnection(serverUrl(), user, password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseUrl(), user, password);
    }

    public String getDatabaseName() {
        return databaseName;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            validatePropertyFileExists(inputStream);
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("database.properties를 읽을 수 없습니다.", e);
        }
    }

    private void validatePropertyFileExists(InputStream inputStream){
        if (inputStream == null) {
            throw new IllegalStateException("database.properties 파일이 존재하지 않습니다.");
        }
    }

    private String serverUrl() {
        return "jdbc:mysql://%s:%s?serverTimezone=Asia/Seoul".formatted(host, port);
    }

    private String databaseUrl() {
        return "jdbc:mysql://%s:%s/%s?serverTimezone=Asia/Seoul".formatted(host, port, databaseName);
    }
}