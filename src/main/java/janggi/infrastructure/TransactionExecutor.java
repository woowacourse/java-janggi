package janggi.infrastructure;

import janggi.infrastructure.function.ConnectionConsumer;
import janggi.infrastructure.function.ConnectionFunction;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutor {

    public static void execute(ConnectionConsumer consumer) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            performTransaction(connection, consumer);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }

    public static <R> R apply(ConnectionFunction<R> function) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            return performTransaction(connection, function);
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 실패", e);
        }
    }

    private static void performTransaction(Connection connection, ConnectionConsumer consumer)
            throws SQLException {
        try {
            connection.setAutoCommit(false);
            consumer.execute(connection);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException("작업 실패로 인한 트랜잭션 롤백", e);
        }
    }

    private static <R> R performTransaction(Connection connection, ConnectionFunction<R> function)
            throws SQLException {
        try {
            connection.setAutoCommit(false);
            R result = function.apply(connection);
            connection.commit();
            return result;
        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException("작업 실패로 인한 트랜잭션 롤백", e);
        }
    }
}
