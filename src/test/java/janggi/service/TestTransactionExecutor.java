package janggi.service;

import janggi.infra.transaction.TransactionExecutor;
import janggi.infra.transaction.action.TransactionRunnable;
import janggi.infra.transaction.action.TransactionSupplier;
import java.sql.Connection;

public class TestTransactionExecutor implements TransactionExecutor {

    private final Connection connection;

    public TestTransactionExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> T execute(TransactionSupplier<T> action) {
        try {
            return action.get(connection);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public void executeWithoutResult(TransactionRunnable action) {
        try {
            action.run(connection);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
