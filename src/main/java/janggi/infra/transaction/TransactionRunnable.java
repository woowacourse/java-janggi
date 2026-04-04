package janggi.infra.transaction;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionRunnable {
    void run(Connection connection) throws Exception;
}