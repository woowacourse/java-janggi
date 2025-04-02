package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseConnector implements DatabaseConnector {

    private static final String SERVER = "jdbc:h2:mem:janggi;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(SERVER, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] H2 DB 연결 오류: " + e.getMessage(), e);
        }
    }
}
