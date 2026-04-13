package service;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionTask<T> {
    T execute(Connection connection) throws SQLException;
}
