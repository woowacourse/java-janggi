package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public final class TransactionExecutor {
    private TransactionExecutor() {
    }

    public static <T> T execute(Function<Connection, T> action) {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            connection.setAutoCommit(false);
            return executeAndCommit(connection, action);
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }
    }

    public static void executeVoid(Consumer<Connection> action) {
        try (Connection connection = DbConnectionFactory.createConnection()) {
            connection.setAutoCommit(false);
            executeAndCommitVoid(connection, action);
        } catch (SQLException e) {
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }
    }

    private static <T> T executeAndCommit(Connection connection, Function<Connection, T> action) {
        try {
            T result = action.apply(connection);
            connection.commit();
            return result;
        } catch (RuntimeException e) {
            rollbackQuietly(connection);
            throw e;
        } catch (SQLException e) {
            rollbackQuietly(connection);
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }
    }

    private static void executeAndCommitVoid(Connection connection, Consumer<Connection> action) {
        try {
            action.accept(connection);
            connection.commit();
        } catch (RuntimeException e) {
            rollbackQuietly(connection);
            throw e;
        } catch (SQLException e) {
            rollbackQuietly(connection);
            throw new IllegalStateException("트랜잭션 처리에 실패했습니다.", e);
        }
    }

    private static void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ignored) {
        }
    }
}
