package service;

import java.sql.Connection;
import java.sql.SQLException;
import repository.jdbc.JdbcConnectionGenerator;

public class TransactionTemplate {
    private final JdbcConnectionGenerator connectionGenerator;

    public TransactionTemplate(JdbcConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    public <T> T execute(TransactionStrategy<T> strategy) {
        try (Connection connection = connectionGenerator.getDBConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = strategy.doTransaction(connection);
                connection.commit();
                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new IllegalStateException(ServiceErrorMessage.ROLL_BACK.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(ServiceErrorMessage.DATABASE_LINKING_FAIL_EXCEPTION.getMessage());
        }
    }
}
