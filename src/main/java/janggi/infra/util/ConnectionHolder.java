package janggi.infra.util;

import java.sql.Connection;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> holder = new ThreadLocal<>();

    public static void bind(Connection connection) {
        holder.set(connection);
    }

    public static Connection get() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }
}
