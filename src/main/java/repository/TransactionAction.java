package repository;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionAction<T> {
    T execute(Connection connection) throws SQLException;
}