package janggi.database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private static boolean isTestMode = false;

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String TEST_DATABASE = "janggi_test";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;

    private DatabaseConnection() {
    }

    public static void setTestMode(final boolean testMode) {
        isTestMode = testMode;
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            final String currentDb = isTestMode ? TEST_DATABASE : DATABASE;
            final String url = "jdbc:mysql://" + SERVER + "/" + currentDb + OPTION;
            return DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("오류가 발생했습니다.");
        }
    }

    public void closeConnection(final Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (final SQLException e) {
                throw new RuntimeException("오류가 발생했습니다.");
            }
        }
    }
}
