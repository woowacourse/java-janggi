package janggi.db;

import janggi.repository.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private static final String DATABASE_ACCESS_FAILED = "[ERROR] 데이터베이스 접근 중 문제가 발생했습니다.";

    private final ConnectionManager connectionManager;

    public TransactionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public <T> T execute(SqlFunction<T> action) {
        try (Connection connection = connectionManager.getConnection()) {
            return action.apply(connection);
        } catch (SQLException e) {
            throw new DataAccessException(DATABASE_ACCESS_FAILED, e);
        }
    }

    public void execute(SqlConsumer action) {
        try (Connection connection = connectionManager.getConnection()) {
            action.accept(connection);
        } catch (SQLException e) {
            throw new DataAccessException(DATABASE_ACCESS_FAILED, e);
        }
    }

    public <T> T executeWithTransaction(SqlFunction<T> action) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            return executeInTransaction(connection, action);
        } catch (SQLException e) {
            throw new DataAccessException(DATABASE_ACCESS_FAILED, e);
        }
    }

    public void executeWithTransaction(SqlConsumer action) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            executeInTransaction(connection, action);
        } catch (SQLException e) {
            throw new DataAccessException(DATABASE_ACCESS_FAILED, e);
        }
    }

    private <T> T executeInTransaction(Connection connection, SqlFunction<T> action) throws SQLException {
        try {
            T result = action.apply(connection);
            connection.commit();
            return result;
        } catch (RuntimeException e) {
            connection.rollback();
            throw e;
        }
    }

    private void executeInTransaction(Connection connection, SqlConsumer action) throws SQLException {
        try {
            action.accept(connection);
            connection.commit();
        } catch (RuntimeException e) {
            connection.rollback();
            throw e;
        }
    }

    @FunctionalInterface
    public interface SqlFunction<T> {
        T apply(Connection connection);
    }

    @FunctionalInterface
    public interface SqlConsumer {
        void accept(Connection connection);
    }
}
