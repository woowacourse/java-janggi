package fixture;

import database.context.ConnectionContext;
import database.transaction.TransactionCallable;
import database.transaction.TransactionExecutor;

import java.sql.Connection;
import java.sql.SQLException;

public class TestTransactionExecutor implements TransactionExecutor {

    public <T> T execute(TransactionCallable<T> callable) {
        try{
            return callable.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
