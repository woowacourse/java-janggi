package transaction;

import config.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

public final class TransactionTemplate {
    private final ConnectionFactory connectionFactory;

    public TransactionTemplate(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException ignored) {
        }
    }

    public <T> T executeInTransaction(Callback<T> callback) {
        try (Connection conn = connectionFactory.getConnection()) {
            boolean previousAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                T result = callback.doInConnection(conn);
                conn.commit();
                return result;
            } catch (SQLException e) {
                rollbackQuietly(conn);
                throw new IllegalStateException("트랜잭션 실패", e);
            } catch (RuntimeException e) {
                rollbackQuietly(conn);
                throw e;
            } finally {
                conn.setAutoCommit(previousAutoCommit);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("커넥션 획득 실패", e);
        }
    }

    @FunctionalInterface
    public interface Callback<T> {
        T doInConnection(Connection connection) throws SQLException;
    }
}
