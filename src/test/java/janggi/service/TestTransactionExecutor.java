package janggi.service;

import janggi.jdbc.transaction.TransactionExecutor;
import janggi.jdbc.transaction.action.TransactionRunnable;
import janggi.jdbc.transaction.action.TransactionSupplier;
import java.sql.Connection;

public class TestTransactionExecutor implements TransactionExecutor {

    private final Connection con;

    public TestTransactionExecutor(Connection con) {
        this.con = con;
    }

    @Override
    public <T> T execute(TransactionSupplier<T> action) {
        try {
            return action.get(con);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public void executeWithoutResult(TransactionRunnable action) {
        try {
            action.run(con);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
