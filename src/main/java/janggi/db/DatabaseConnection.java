package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final DatabaseConfig config;

    public DatabaseConnection(final DatabaseConfig config) {
        this.config = config;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    config.getConnectionUrl(),
                    config.getUsername(),
                    config.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결에 실패했습니다.");
        }
    }
}
