package janggi.infra.transaction;

@FunctionalInterface
public interface TransactionalCallback<T> {
    T doInTransaction();
}
