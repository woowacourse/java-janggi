package janggi.infra.transaction;

import janggi.infra.ConnectionProvider;
import janggi.infra.transaction.action.TransactionRunnable;
import janggi.infra.transaction.action.TransactionSupplier;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutorImpl implements TransactionExecutor {

    private final ConnectionProvider connectionProvider;

    public TransactionExecutorImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public <T> T execute(TransactionSupplier<T> action) {
        try (Connection connection = connectionProvider.getConnection()) {
            beginTransaction(connection);

            try {
                T result = action.get(connection);
                commit(connection);
                return result;
            } catch (RuntimeException e) {
                rollback(connection);
                throw e;
            } finally {
                endTransaction(connection);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }

    }

    @Override
    public void executeWithoutResult(TransactionRunnable action) {
        try (Connection connection = connectionProvider.getConnection()) {
            beginTransaction(connection);

            try {
                action.run(connection);
                commit(connection);
            } catch (RuntimeException e) {
                rollback(connection);
                throw e;
            } finally {
                endTransaction(connection);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }
    }

    private void beginTransaction(Connection connection) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 시작에 실패했습니다.", e);
        }
    }

    private void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
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

    private void endTransaction(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new IllegalStateException("autoCommit 복구에 실패했습니다.", e);
        }
    }
}
