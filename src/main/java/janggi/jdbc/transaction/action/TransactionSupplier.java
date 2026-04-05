package janggi.jdbc.transaction.action;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionSupplier<T> {
    T get(Connection connection);
}
