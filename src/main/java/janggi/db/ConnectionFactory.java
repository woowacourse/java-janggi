package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:janggi.db";

    public Connection create() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException exception) {
            throw new IllegalStateException("데이터베이스 연결에 실패했습니다.", exception);
        }
    }
}
