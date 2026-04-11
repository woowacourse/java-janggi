package janggi.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {
    private static final String URL = "jdbc:h2:~/janggi;MODE=MySQL";
    private static final String USER = "ethan";
    private static final String PASSWORD = "ethan";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }
}
