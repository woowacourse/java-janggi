package janggi.infra.transaction;

import java.sql.SQLException;

@FunctionalInterface
public interface TransactionCallback<T> {
    T doInTransaction() throws SQLException;
}
