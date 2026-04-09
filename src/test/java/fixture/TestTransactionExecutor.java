package fixture;

import database.exception.DataAccessException;
import database.transaction.TransactionCallable;
import database.transaction.TransactionExecutor;

public class TestTransactionExecutor implements TransactionExecutor {

    public <T> T execute(TransactionCallable<T> callable) {
        try{
            return callable.execute();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
