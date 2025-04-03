package janggi.domain.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final String SERVER = "localhost:03306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";


    private static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new RuntimeException("DB 연결에 실패했습니다.", e);
        }
    }

    public static PreparedStatement getPreparedStatement(String query) {
        try {
            return getConnection().prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException("PreparedStatement 생성에 실패했습니다.", e);
        }
    }
}
