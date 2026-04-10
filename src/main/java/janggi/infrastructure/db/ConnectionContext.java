package janggi.infrastructure.db;

import java.sql.Connection;

public class ConnectionContext {
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    public static void set(Connection connection) {
        CONNECTION_HOLDER.set(connection);
    }

    public static Connection get() {
        return CONNECTION_HOLDER.get();
    }

    public static void clear() {
        CONNECTION_HOLDER.remove();
    }
}
