package janggi.repository.util.transaction;

import janggi.repository.util.connection.ConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public class JdbcTransactionManager implements TransactionManager {

    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private final ConnectionProvider connectionProvider;

    public JdbcTransactionManager(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public <T> T executeInTransaction(Supplier<T> action) {
        boolean isNewTransaction = isNotActive();
        if (isNewTransaction) {
            begin();
        }
        try {
            T result = action.get();
            if (isNewTransaction) {
                commit();
            }
            return result;
        } catch (RuntimeException e) {
            if (isNewTransaction) {
                rollback();
            }
            throw e;
        } finally {
            if (isNewTransaction) {
                close();
            }
        }
    }

    public void executeInTransaction(Runnable action) {
        boolean isNewTransaction = isNotActive();
        if (isNewTransaction) {
            begin();
        }
        try {
            action.run();
            if (isNewTransaction) {
                commit();
            }
        } catch (RuntimeException e) {
            if (isNewTransaction) {
                rollback();
            }
            throw e;
        } finally {
            if (isNewTransaction) {
                close();
            }
        }
    }

    private void begin() {
        try {
            Connection conn = connectionProvider.getConnection();
            conn.setAutoCommit(false);
            connectionHolder.set(conn);
        } catch (SQLException e) {
            throw new RuntimeException("트랜잭션 시작 실패", e);
        }
    }

    public Connection getConnection() {
        Connection conn = connectionHolder.get();
        if (conn == null) {
            throw new IllegalStateException("동기화된 트랜잭션 커넥션이 존재하지 않습니다.");
        }
        return conn;
    }

    private void commit() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException("트랜잭션 커밋 실패", e);
            }
        }
    }

    private void rollback() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("트랜잭션 롤백 실패", e);
            }
        }
    }

    private void close() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("커넥션 종료 실패", e);
            } finally {
                connectionHolder.remove();
            }
        }
    }

    private boolean isNotActive() {
        return connectionHolder.get() == null;
    }
}
