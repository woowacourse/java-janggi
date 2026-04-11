package janggi.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/janggi";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DBConnector() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] DB 연결 실패", e);
        }
    }
}
