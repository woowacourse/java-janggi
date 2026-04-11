package infrastructure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionManager {
    private static final String FAIL_TO_TRANSACTION = "[ERROR] 트랜잭션에 실패했습니다.";
    private final JdbcConnectionManager jdbcConnectionManager;

    public TransactionManager(JdbcConnectionManager jdbcConnectionManager) {
        this.jdbcConnectionManager = jdbcConnectionManager;
    }

    public <T> T transaction(Function<Connection, T> function) {
        try (
                Connection connection = jdbcConnectionManager.getConnection();
        ) {
            connection.setAutoCommit(false);
            try {
                T result = function.apply(connection);
                connection.commit();
                return result;
            } catch (Exception exception) {
                connection.rollback();
                throw new IllegalStateException(FAIL_TO_TRANSACTION, exception);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_TRANSACTION, exception);
        }
    }

    public void transaction(Consumer<Connection> function) {
        try (
                Connection connection = jdbcConnectionManager.getConnection();
        ) {
            connection.setAutoCommit(false);
            try {
                function.accept(connection);
                connection.commit();
            } catch (Exception exception) {
                connection.rollback();
                throw new IllegalStateException(FAIL_TO_TRANSACTION, exception);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException(FAIL_TO_TRANSACTION, exception);
        }
    }
}
