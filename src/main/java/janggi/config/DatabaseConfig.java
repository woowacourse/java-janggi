package janggi.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DatabaseConfig.class.getResourceAsStream("/application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties 파일을 찾을 수 없습니다.");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("JDBC 초기화 실패", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.id"),
                    properties.getProperty("db.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }
}
