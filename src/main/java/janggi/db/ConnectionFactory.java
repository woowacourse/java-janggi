package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String DEFAULT_URL = "jdbc:sqlite:janggi.db";
    private final String url;

    public ConnectionFactory() {
        this(DEFAULT_URL);
    }

    public ConnectionFactory(String url) {
        this.url = url;
    }

    public Connection create() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new DatabaseException("데이터베이스 연결에 실패했습니다.", e);
        }
    }
}
