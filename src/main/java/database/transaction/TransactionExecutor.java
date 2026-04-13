package database.transaction;

@FunctionalInterface
public interface TransactionExecutor {
    <T> T execute(TransactionCallable<T> callable);
}
