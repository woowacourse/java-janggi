package janggi.repository.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private final ConnectionProvider connectionProvider;

    public TransactionManager(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
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

    public Connection getConnection() {
        Connection conn = connectionHolder.get();
        if (conn == null) {
            throw new IllegalStateException("동기화된 트랜잭션 커넥션이 존재하지 않습니다.");
        }
        return conn;
    }

    public void commit() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException("트랜잭션 커밋 실패", e);
            }
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

    public boolean isNotActive() {
        return connectionHolder.get() == null;
    }
}
