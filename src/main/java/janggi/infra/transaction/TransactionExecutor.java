package janggi.infra.transaction;

import janggi.infra.ConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutor {

    private final ConnectionProvider connectionProvider;

    public TransactionExecutor(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public <T> T execute(TransactionSupplier<T> action) {
        try(Connection con = connectionProvider.getConnection()) {
            beginTransaction(con);

            try {
                T result = action.get(con);
                commit(con);
                return result;
            } catch (Exception e) {
                rollback(con);
                throw new IllegalStateException("주어진 로직을 실행하는데 실패했습니다.", e);
            } finally {
                endTransaction(con);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }

    }

    public void executeWithoutResult(TransactionRunnable action) {
        try(Connection con = connectionProvider.getConnection()) {
            beginTransaction(con);

            try {
                action.run(con);
                commit(con);
            } catch (Exception e) {
                rollback(con);
                throw new IllegalStateException("주어진 로직을 실행하는데 실패했습니다.", e);
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
