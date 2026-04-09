package database.context;

import database.connection.DBConnector;
import database.exception.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionContext {

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private ConnectionContext() {
    }

    public static void setConnection() {
        if (getConnection() != null) {
            clear();
        }
        try {
            Connection connection = DBConnector.getConnection();
            connection.setAutoCommit(false);
            CONNECTION_THREAD_LOCAL.set(connection);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static Connection getConnection() {
        return CONNECTION_THREAD_LOCAL.get();
    }

    public static void rollback() {
        try {
            safetyConnectionRollback(getConnection());
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static void clear() {
        try {
            Connection connection = getConnection();
            safetyConnectionClose(connection);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        } finally {
            CONNECTION_THREAD_LOCAL.remove();
        }
    }

    public static void commit() {
        try {
            Connection connection = getConnection();
            safetyConnectionCommit(connection);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private static void safetyConnectionCommit(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.commit();
        }
    }

    private static void safetyConnectionClose(Connection connection) throws SQLException{
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    private static void safetyConnectionRollback(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.rollback();
        }
    }

}
