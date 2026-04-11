package database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/jangi-db";
    private static final String DEFAULT_USER = "jangi";
    private static final String DEFAULT_PASSWORD = "jangi";

    private final String url;
    private final String user;
    private final String password;

    public DatabaseConnector() {
        this(DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD);
    }

    public DatabaseConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }
}
