package service;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionStrategy<T> {
    T doTransaction(Connection connection) throws Exception;
}
