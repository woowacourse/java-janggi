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
        try(Connection con = connectionProvider.getConnection()) {
            beginTransaction(con);

            try {
                T result = action.get(con);
                commit(con);
                return result;
            } catch (RuntimeException e) {
                rollback(con);
                throw e;
            } finally {
                endTransaction(con);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }

    }

    @Override
    public void executeWithoutResult(TransactionRunnable action) {
        try(Connection con = connectionProvider.getConnection()) {
            beginTransaction(con);

            try {
                action.run(con);
                commit(con);
            } catch (RuntimeException e) {
                rollback(con);
                throw e;
            } finally {
                endTransaction(con);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }
    }

    private void beginTransaction(Connection con) {
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 시작에 실패했습니다.", e);
        }
    }

    private void commit(Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            throw new IllegalStateException("커밋에 실패했습니다.", e);
        }
    }

    private void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new IllegalStateException("롤백에 실패했습니다.", e);
        }
    }

    private void endTransaction(Connection con) {
        try {
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new IllegalStateException("autoCommit 복구에 실패했습니다.", e);
        }
    }
}
