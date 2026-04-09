package support;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionCallback<T> {

    T doInTransaction(Connection connection);
}
