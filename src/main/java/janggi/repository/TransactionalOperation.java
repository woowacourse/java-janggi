package janggi.repository;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionalOperation<T> {
    T execute(Connection connection) throws Exception;
}
