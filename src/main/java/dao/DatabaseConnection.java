package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new RuntimeException("properties 파일을 찾을 수 없음");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("설정 파일 로드 오류", e);
        }
    }


    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + properties.getProperty("db.server") + "/" + properties.getProperty("db.name")
                + properties.getProperty("db.options");
        return DriverManager.getConnection(url, properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }
}
