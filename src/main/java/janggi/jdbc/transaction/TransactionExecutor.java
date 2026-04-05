package janggi.jdbc.transaction;

import janggi.jdbc.transaction.action.TransactionRunnable;
import janggi.jdbc.transaction.action.TransactionSupplier;

public interface TransactionExecutor {

    <T> T execute(TransactionSupplier<T> action);

    void executeWithoutResult(TransactionRunnable action);
}
