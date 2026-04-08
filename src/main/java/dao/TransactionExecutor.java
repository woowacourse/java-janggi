package dao;

import db.DbConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public final class TransactionExecutor {
    private TransactionExecutor() {
    }

    public static <T> T execute(Function<Connection, T> action) {
        Connection connection = null;
        try {
            connection = DbConnectionFactory.createConnection();
            connection.setAutoCommit(false);
            T result = action.apply(connection);
            connection.commit();
            return result;
        } catch (Exception e) {
            rollbackQuietly(connection);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        } finally {
            closeQuietly(connection);
        }
    }

    private static void closeQuietly(Connection connection) {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException ignored) {
        }
    }

    private static void rollbackQuietly(Connection connection) {
        if (connection == null) return;
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }
}
