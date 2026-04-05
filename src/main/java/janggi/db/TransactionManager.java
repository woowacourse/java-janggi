package janggi.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionManager {
    private static final String FAILED_TRANSACTION_MESSAGE = "트랜잭션 실행 중 오류 발생";
    private static final String FAILED_ROLLBACK_MESSAGE = "롤백 중 오류 발생";

    private final SQLManager sqlManager;

    public TransactionManager(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
    }

    public <T> T sync(Function<Connection, T> function) {
        Connection connection = sqlManager.ensureConnection();
        try {
            T result = function.apply(connection);
            connection.commit();
            return result;
        } catch (Exception e) {
            rollback(connection);
            throw new RuntimeException(FAILED_TRANSACTION_MESSAGE, e);
        }
    }

    public void sync(Consumer<Connection> consumer) {
        Connection connection = sqlManager.ensureConnection();
        try {
            consumer.accept(connection);
            connection.commit();
        } catch (Exception e) {
            rollback(connection);
            throw new RuntimeException(FAILED_TRANSACTION_MESSAGE, e);
        }
    }

    private void rollback(Connection connection) {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(FAILED_ROLLBACK_MESSAGE, e);
        }
    }
}
