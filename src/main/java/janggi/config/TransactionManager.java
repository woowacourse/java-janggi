package janggi.config;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private final ConnectionPool pool;

    public TransactionManager(ConnectionPool pool) {
        this.pool = pool;
    }

    public <T> T execute(TransactionCallback<T> callback) {
        PooledConnection pooledConn = pool.getPooledConnection();
        Connection conn = pooledConn.getConnection();
        try {
            conn.setAutoCommit(false);
            T result = callback.run(conn);
            conn.commit();
            return result;
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            throw new RuntimeException("트랜잭션 실행 중 오류가 발생했습니다.", e);
        } finally {
            pooledConn.close();
        }
    }
}
