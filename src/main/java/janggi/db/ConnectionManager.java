package janggi.db;

import janggi.repository.DataAccessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String DATABASE_CONNECTION_FAILED = "[ERROR] 데이터베이스 연결 중 문제가 발생했습니다.";

    private final String url;
    private final String user;
    private final String password;

    public ConnectionManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataAccessException(DATABASE_CONNECTION_FAILED, e);
        }
    }
}
