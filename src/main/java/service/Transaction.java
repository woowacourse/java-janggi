package service;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface Transaction<T> {
    T execute(Connection connection) throws SQLException;
}
