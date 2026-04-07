package global.template;

import java.sql.Connection;

public interface TransactionCallback<T> {
    T doInTransaction(Connection connection) throws Exception;
}
