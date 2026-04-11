package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DBConnector implements DBConnector {

    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private final String url;

    public H2DBConnector() {
        url = "jdbc:h2:./janggi";
    }

    public H2DBConnector(String url) {
        this.url = url;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 접속 에러");
        }
    }
}
