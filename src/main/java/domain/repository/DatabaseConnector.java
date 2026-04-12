package domain.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3307/jangi_db";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private DatabaseConnector() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }
}
