package janggi.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final String DRIVER = "jdbc:mysql";
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private DatabaseConnection() {
    }

    public static boolean isConnected() {
        try (Connection connection = createConnection()) {
            return connection.isValid(3);
        } catch (Exception e) {
            return false;
        }
    }

    public static Connection createConnection() {
        try {
            String url = String.format("%s://%s/%s%s", DRIVER, SERVER, DATABASE, OPTION);
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
