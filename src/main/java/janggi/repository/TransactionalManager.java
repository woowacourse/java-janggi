package janggi.repository;

import janggi.infrastructure.JdbcConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionalManager {

    private final JdbcConnectionManager connectionManager;

    public TransactionalManager(JdbcConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public <T> T executeInTransaction(TransactionalOperation<T> operation) {
        Connection connection = null;
        try {
            connection = connectionManager.getConnection();
            connection.setAutoCommit(false);

            T result = operation.execute(connection);
            connection.commit();
            return result;
        } catch (Exception e) {
            rollback(connection);
            throw new IllegalStateException("[ERROR] Transaction 실패 : " + e.getMessage(), e);
        } finally {
            resetAutoCommit(connection);
            close(connection);
        }
    }

    private void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] Rollback 실패 : " + e.getMessage(), e);
        }
    }

    private void resetAutoCommit(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] AutoCommit 초기화 실패 : " + e.getMessage(), e);
        }
    }

    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] Connection 종료 실패 : " + e.getMessage(), e);
        }
    }
}
