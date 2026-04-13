package database.transaction;

import database.context.ConnectionContext;
import database.exception.DataAccessException;

public class JanggiTransactionExecutor implements TransactionExecutor {

    public <T> T execute(TransactionCallable<T> callable) {
        try {
            ConnectionContext.setConnection();
            T execute = callable.execute();
            ConnectionContext.commit();
            return execute;
        } catch (DataAccessException e) {
            ConnectionContext.rollback();
            throw e;
        } finally {
            ConnectionContext.clear();
        }
    }

}
