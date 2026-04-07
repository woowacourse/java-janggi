package database.transaction;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallable<T> {
    T execute() throws SQLException;
}
