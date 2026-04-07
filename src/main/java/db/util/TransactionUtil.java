package db.util;

import db.connector.Connector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionUtil {

    private TransactionUtil() {
    }

    public static void withTransaction(
            Connector connector,
            String sql,
            StatementMode statementMode,
            TransactionalRunnable work
    ) {
        try (
                Connection connection = connector.getConnection();
                PreparedStatement statement = statementMode.prepare(connection, sql)
        ) {
            rollbackIfThrowException(work, connection, statement);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static <T> T withTransaction(
            Connector connector,
            String sql,
            StatementMode statementMode,
            TransactionalFunction<T> work
    ) {
        try (
                Connection connection = connector.getConnection();
                PreparedStatement statement = statementMode.prepare(connection, sql)
        ) {
            return rollbackIfThrowException(work, connection, statement);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private static void rollbackIfThrowException(
            TransactionalRunnable work,
            Connection connection,
            PreparedStatement statement
    ) throws SQLException {
        try {
            connection.setAutoCommit(false);

            work.execute(connection, statement);

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private static <T> T rollbackIfThrowException(
            TransactionalFunction<T> work,
            Connection connection,
            PreparedStatement statement
    ) throws SQLException {
        try {
            connection.setAutoCommit(false);
            T result = work.execute(connection, statement);
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
