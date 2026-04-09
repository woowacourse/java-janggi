package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private final String url;

    public DatabaseConnector() {
        url = "jdbc:h2:./janggi";
    }

    public DatabaseConnector(String url) {
        this.url = url;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접속 에러");
        }
    }
}
