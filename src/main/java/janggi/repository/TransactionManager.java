package janggi.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public class TransactionManager {

    private static final ThreadLocal<Connection> connectionHandler = new ThreadLocal<>();

    private final JdbcContext jdbcContext;

    public TransactionManager(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void execute(Runnable callback) {
        executeInTransaction(() -> {
            callback.run();
            return null;
        });
    }

    public <T> T execute(Supplier<T> callback) {
        return executeInTransaction(callback);
    }

    private <T> T executeInTransaction(Supplier<T> callback) {
        try (Connection conn = jdbcContext.getConnection()) {
            conn.setAutoCommit(false);
            connectionHandler.set(conn);

            try {
                T result = callback.get();
                conn.commit();
                return result;
            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException(e);
            } finally {
                connectionHandler.remove();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connectionHandler.get();
    }
}
