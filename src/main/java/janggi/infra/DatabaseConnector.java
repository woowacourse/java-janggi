package janggi.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private final DatabaseConfig DBConfig;

    public DatabaseConnector(DatabaseConfig DBConfig) {
        this.DBConfig = DBConfig;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DBConfig.getUrl(), DBConfig.getUsername(),
                    DBConfig.getPassword());
        } catch (final SQLException e) {
            throw new RuntimeException("DB 연결 오류:" + e.getMessage());
        }
    }
}
