package janggi.infra.transaction;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionSupplier<T> {
    T get(Connection connection) throws Exception;
}
