package service;

import java.sql.Connection;

public interface TransactionStrategy<T> {
    T doTransaction(Connection connection) throws Exception;
}
