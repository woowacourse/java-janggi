package janggi.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private final BlockingQueue<Connection> pool;
    private final String url;
    private final String username;
    private final String password;
    private final long timeoutMillis;

    private final int poolSize;

    ConnectionPool(String url, String username, String password, int poolSize) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.timeoutMillis = 5000;
        this.poolSize = poolSize;

        this.pool = new ArrayBlockingQueue<>(this.poolSize);

        try {
            for (int i = 0; i < this.poolSize; i++) {
                pool.offer(createConnection());
            }
        } catch (SQLException e) {
            throw new RuntimeException("커넥션 풀 초기화 실패", e);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        try {
            Connection conn = pool.poll(timeoutMillis, TimeUnit.MILLISECONDS);
            if (conn == null) {
                throw new RuntimeException("커넥션 획득 대기 시간 초과 (Timeout)");
            }

            if (!conn.isValid(1)) {
                try {
                    conn.close();
                } catch (SQLException ignored) {}
                conn = createConnection();
            }

            return conn;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("커넥션 대기 중 인터럽트 발생", e);
        } catch (SQLException e) {
            throw new RuntimeException("유효하지 않은 커넥션 재설정 및 검증 실패", e);
        }
    }

    public void release(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.setAutoCommit(true);
                    pool.offer(conn);
                }
            } catch (SQLException e) {
                try {
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    public void shutdown() {
        Connection conn;
        while ((conn = pool.poll()) != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {}
        }
    }

    public PooledConnection getPooledConnection() {
        return new PooledConnection(getConnection(), this);
    }

    public int getAvailableCount() {
        return pool.size();
    }

    public int getActiveCount() {
        return poolSize - pool.size();
    }
}