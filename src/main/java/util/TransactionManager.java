package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class TransactionManager {
    private final ConnectionFactory connectionFactory;

    public TransactionManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(Function<Connection, T> action) {
        try (Connection connection = connectionFactory.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = action.apply(connection);
                connection.commit();
                return result;
            } catch (Exception e) {
                rollback(connection);
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("커넥션을 닫지 못했습니다.");
        }
    }

    public void executeWithoutResult(Consumer<Connection> action) {
        execute(connection -> {
            action.accept(connection);
            return null;
        });
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("트랜잭션 롤백에 실패했습니다.");
        }
    }
}
