package janggi.repository.util.transaction;

import java.sql.Connection;
import java.util.function.Supplier;

public interface TransactionManager {

    <T> T executeInTransaction(Supplier<T> action);

    void executeInTransaction(Runnable action);

    Connection getConnection();
}
