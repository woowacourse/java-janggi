package janggi.jdbc.transaction.action;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionRunnable {
    void run(Connection connection);
}