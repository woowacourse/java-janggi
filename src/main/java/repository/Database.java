package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:h2:./data/janggidb;AUTO_SERVER=TRUE";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static <T> T executeInTransaction(TransactionAction<T> action, String errorMessage) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = action.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(errorMessage, e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(errorMessage, e);
        }
    }
}
