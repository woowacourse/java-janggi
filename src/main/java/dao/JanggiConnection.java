package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JanggiConnection {
    private final String server;
    private final String database;
    private final String options;
    private final String username;
    private final String password;

    public JanggiConnection() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            this.server = prop.getProperty("database.host") + ":" + prop.getProperty("database.port");
            this.database = prop.getProperty("database.name");
            this.options = prop.getProperty("database.options");
            this.username = prop.getProperty("database.username");
            this.password = prop.getProperty("database.password");

        } catch (Exception e) {
            throw new RuntimeException("설정 파일 로딩 실패", e);
        }
    }

    public Connection getConnection() {
        try {
            String url = "jdbc:mysql://" + server + "/" + database + "?" + options;
            return DriverManager.getConnection(url, username, password);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            return null;
        }
    }
}
