package janggi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection implements DatabaseConnection {

    private static MySQLDatabaseConnection instance;

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION;

    private MySQLDatabaseConnection() {
    }

    public static MySQLDatabaseConnection getInstance() {
        if (instance == null) {
            instance = new MySQLDatabaseConnection();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터 베이스 연결에 실패했습니다.", e);
        }
    }
}
