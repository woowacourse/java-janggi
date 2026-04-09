package database.transaction;

import database.context.ConnectionContext;

import java.sql.Connection;
import java.sql.SQLException;

public class JanggiTransactionExecutor implements TransactionExecutor {

    public <T> T execute(TransactionCallable<T> callable) {
        try{
            ConnectionContext.setConnection();
            Connection connection = ConnectionContext.getConnection();
            connection.setAutoCommit(false);

            T execute = callable.execute();

            connection.commit();
            return execute;
        } catch (SQLException e) {
            ConnectionContext.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            ConnectionContext.clear();
        }
    }

}
