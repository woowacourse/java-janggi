package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class DBConnectionManager {

    private static final String SERVER = "localhost:3306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0000";

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }

    public static void useDBConnection(Consumer<PreparedStatement> action, String query) {
        try (Connection connection = DBConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            action.accept(preparedStatement);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }

    public static <T> T useDBConnection(Function<PreparedStatement, T> action, String query) {
        try (Connection connection = DBConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return action.apply(preparedStatement);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }

    public static <T> T useDBConnectionWithStatement(Function<PreparedStatement, T> action, String query,
        int statementConstant) {
        try (Connection connection = DBConnectionManager.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, statementConstant)) {
            return action.apply(preparedStatement);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결 오류:" + e.getMessage());
        }
    }
}
