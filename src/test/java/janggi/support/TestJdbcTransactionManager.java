package janggi.support;

import janggi.repository.util.connection.ConnectionProvider;
import janggi.repository.util.transaction.TransactionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public class TestJdbcTransactionManager implements TransactionManager {

    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private final ConnectionProvider connectionProvider;

    public TestJdbcTransactionManager(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public <T> T executeInTransaction(Supplier<T> action) {
        return action.get();
    }

    @Override
    public void executeInTransaction(Runnable action) {
        action.run();
    }

    @Override
    public Connection getConnection() {
        Connection conn = connectionHolder.get();
        if (conn == null) {
            throw new IllegalStateException("동기화된 트랜잭션 커넥션이 존재하지 않습니다.");
        }
        return conn;
    }

    public void begin() {
        try {
            Connection conn = connectionProvider.getConnection();
            conn.setAutoCommit(false);
            connectionHolder.set(conn);
        } catch (SQLException e) {
            throw new RuntimeException("트랜잭션 시작 실패", e);
        }
    }

    public void rollback() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException("트랜잭션 롤백 실패", e);
            }
        }
    }

    public void close() {
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
}
