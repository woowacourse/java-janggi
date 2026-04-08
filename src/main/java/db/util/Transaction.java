package db.util;

import db.connector.Connector;
import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

    private final Connector connector;

    public Transaction(Connector connector) {
        this.connector = connector;
    }

    public void execute(
            TransactionalRunnable work
    ) {
        try (Connection connection = connector.getConnection()) {
            rollbackIfThrowException(work, connection);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T execute(
            TransactionalFunction<T> work
    ) {
        try (Connection connection = connector.getConnection()) {
            return rollbackIfThrowException(work, connection);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private void rollbackIfThrowException(
            TransactionalRunnable work,
            Connection connection
    ) throws SQLException {
        try {
            connection.setAutoCommit(false);

            work.execute(connection);

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private <T> T rollbackIfThrowException(
            TransactionalFunction<T> work,
            Connection connection
    ) throws SQLException {
        try {
            connection.setAutoCommit(false);
            T result = work.execute(connection);
            connection.commit();

            return result;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
