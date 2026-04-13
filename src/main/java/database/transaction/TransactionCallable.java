package database.transaction;

import database.exception.DataAccessException;

@FunctionalInterface
public interface TransactionCallable<T> {
    T execute() throws DataAccessException;
}
