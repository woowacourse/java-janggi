package database.transaction;

public interface TransactionExecutor {

    <T> T execute(TransactionCallable<T> callable);

}
