package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:h2:./janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접속 에러");
        }
    }

    public static Connection getConnection(String testUrl) {
        try {
            return DriverManager.getConnection(testUrl, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접속 에러");
        }
    }
}
