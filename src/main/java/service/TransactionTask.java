package service;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionTask<T> {
    T execute(Connection connection) throws SQLException;
}
