package janggi.infra.transaction;

import janggi.infra.transaction.action.TransactionRunnable;
import janggi.infra.transaction.action.TransactionSupplier;

public interface TransactionExecutor {

    <T> T execute(TransactionSupplier<T> action);

    void executeWithoutResult(TransactionRunnable action);
}
