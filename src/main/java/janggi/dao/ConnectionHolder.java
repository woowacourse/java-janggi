package janggi.dao;

import java.sql.Connection;

public class ConnectionHolder {
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Connection conn) {
        THREAD_LOCAL.set(conn);
    }

    public static Connection get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
