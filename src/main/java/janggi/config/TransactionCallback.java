package janggi.config;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback<T> {
    T run(Connection conn) throws SQLException;
}
