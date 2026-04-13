package data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private final DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T executeTransaction(TransactionWork<T> transactionWork) {
        Connection connection = getConnection();
        try {
            connection.setAutoCommit(false);
            T result = transactionWork.run(connection);

            connection.commit();
            return result;
        } catch (Exception e) {
            rollback(connection);
            throw new RuntimeException(e);
        } finally {
            close(connection);
        }
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @FunctionalInterface
    public interface TransactionWork<T> {
        T run(Connection connection) throws Exception;
    }
}
