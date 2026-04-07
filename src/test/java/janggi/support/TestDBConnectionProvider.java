package janggi.support;

import janggi.repository.util.ConnectionProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnectionProvider implements ConnectionProvider {

    private static final String URL = "jdbc:h2:./java-janggi-test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 실패", e);        }
    }
}
