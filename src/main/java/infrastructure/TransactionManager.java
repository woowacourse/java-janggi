package infrastructure;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionManager {
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
            } catch (SQLException exception) {
                connection.rollback();
                throw new IllegalStateException("[ERROR] 트랜잭션에 실패했습니다.", exception);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 트랜잭션에 실패했습니다.", exception);
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
            } catch (SQLException exception) {
                connection.rollback();
                throw new IllegalStateException("[ERROR] 트랜잭션에 실패했습니다.", exception);
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("[ERROR] 트랜잭션에 실패했습니다.", exception);
        }
    }
}
