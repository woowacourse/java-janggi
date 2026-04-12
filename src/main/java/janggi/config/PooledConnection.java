package janggi.config;

import java.sql.Connection;

public class PooledConnection implements AutoCloseable{
    private final Connection conn;
    private final ConnectionPool pool;

    PooledConnection(Connection conn, ConnectionPool pool) {
        this.conn = conn;
        this.pool = pool;
    }

    public Connection getConnection() {
        return conn;
    }

    @Override
    public void close() {
        pool.release(conn);
    }
}
