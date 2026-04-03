package janggi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class H2ConnectionManager {

    private static final String URL = "jdbc:h2:file:./data/janggi";
    private static final String USER = "stark";
    private static final String PASSWORD = "stark123!";

    private static final String UNABLE_TO_ACCESS_DATABASE = "[ERROR] H2 데이터베이스에 연결할 수 없습니다.";

    public Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException(UNABLE_TO_ACCESS_DATABASE);
        }
    }
}
