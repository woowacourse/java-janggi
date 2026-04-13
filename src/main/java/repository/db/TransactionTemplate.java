package repository.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class TransactionTemplate {
    private final DataSource dataSource;

    public TransactionTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(TransactionCallback<T> callback) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            ConnectionManager.set(conn);

            try {
                T result = callback.execute();
                conn.commit();
                return result;
            } catch (Exception e) {
                conn.rollback();
                throw convertToRuntimeException(e);
            } finally {
                ConnectionManager.remove();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(TransactionRunnable runnable) {
        execute(() -> {
            runnable.execute();
            return null;
        });
    }

    private RuntimeException convertToRuntimeException(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }

    @FunctionalInterface
    public interface TransactionCallback<T> {
        T execute() throws Exception;
    }

    @FunctionalInterface
    public interface TransactionRunnable {
        void execute() throws Exception;
    }
}