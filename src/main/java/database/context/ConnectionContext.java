package database.context;

import database.connection.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionContext {

    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    public static void setConnection() throws SQLException {
        if (getConnection() != null) {
            clear();
        }
        Connection connection = DBConnector.getConnection();
        CONNECTION_THREAD_LOCAL.set(connection);
    }

    public static Connection getConnection() {
        return CONNECTION_THREAD_LOCAL.get();
    }

    public static void rollback() {
        try {
            if (getConnection() != null) {
                getConnection().rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        try {
            Connection connection = getConnection();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_THREAD_LOCAL.remove();
        }

    }

}
