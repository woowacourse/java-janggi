package janggi.repository;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionalService {

    public <T> T executeInTransaction(Connection connection, TransactionalOperation<T> operation) {
        try {
            connection.setAutoCommit(false);
            T result = operation.execute(connection);
            connection.commit();
            return result;
        } catch (Exception e) {
            rollback(connection);
            throw new IllegalStateException("[ERROR] Transaction 실패 : " + e.getMessage(), e);
        } finally {
            resetAutoCommit(connection);
        }
    }

    private void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] RollBack 실패 : " + e.getMessage());
        }
    }

    private void resetAutoCommit(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] Set Auto Commit 실패 : " + e.getMessage());
        }
    }
}
