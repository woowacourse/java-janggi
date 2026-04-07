package database.connection;

import database.dao.TransactionRunnable;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutor {

    public void execute(TransactionRunnable runnable) {
        try{
            ConnectionContext.setConnection();
            Connection connection = ConnectionContext.getConnection();
            connection.setAutoCommit(false);

            runnable.run();

            connection.commit();
        } catch (SQLException e) {
            ConnectionContext.rollback();
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            ConnectionContext.clear();
        }
    }

}
