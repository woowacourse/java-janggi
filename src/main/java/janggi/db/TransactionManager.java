package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;

public final class TransactionManager {

    private final ConnectionManager connectionManager;

    public TransactionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public <T> T readOnly(SqlFunction<T> action) throws SQLException {
        try (Connection connection = connectionManager.createConnection()) {
            return action.apply(connection);
        }
    }

    public <T> T inTransaction(SqlFunction<T> action) throws SQLException {
        try (Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);

            try {
                T result = action.apply(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public void inTransaction(SqlConsumer action) throws SQLException {
        try (Connection connection = connectionManager.createConnection()) {
            connection.setAutoCommit(false);

            try {
                action.accept(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    @FunctionalInterface
    public interface SqlFunction<T> {
        T apply(Connection connection) throws SQLException;
    }

    @FunctionalInterface
    public interface SqlConsumer {
        void accept(Connection connection) throws SQLException;
    }
}
