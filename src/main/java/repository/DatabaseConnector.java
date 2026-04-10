package repository;

import common.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String DEFAULT_URL = "jdbc:h2:./data/janggi";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        return getConnection(DEFAULT_URL);
    }

    public static Connection getConnection(String url) {
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseException("데이터베이스 연결에 실패했습니다.");
        }
    }
}
