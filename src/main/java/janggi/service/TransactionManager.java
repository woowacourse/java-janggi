package janggi.service;

import janggi.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public void execute(Runnable action) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            run(connection, action);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }

    private void run(Connection connection, Runnable action) throws SQLException {
        connectionHolder.set(connection);
        connection.setAutoCommit(false);
        try {
            action.run();
            commit(connection);
        } catch (IllegalArgumentException | IllegalStateException e) {
            rollback(connection);
            throw e;
        } catch (RuntimeException e) {
            rollback(connection);
            throw new IllegalStateException("트랜잭션 처리 중 오류가 발생했습니다.", e);
        } finally {
            connectionHolder.remove();
        }
    }

    private void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            throw new IllegalStateException("커밋에 실패했습니다.", e);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new IllegalStateException("롤백에 실패했습니다.", e);
        }
    }

    public Connection getConnection() {
        return connectionHolder.get();
    }
}
