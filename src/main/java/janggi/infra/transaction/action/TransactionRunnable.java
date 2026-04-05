package janggi.infra.transaction.action;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionRunnable {
    void run(Connection connection);
}