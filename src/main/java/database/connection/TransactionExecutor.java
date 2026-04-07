package database.connection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionExecutor {

    public void execute(Runnable runnable) {
        try{
            ConnectionContext.setConnection();
            Connection connection = ConnectionContext.getConnection();
            connection.setAutoCommit(false);

            runnable.run();

            connection.commit();
        } catch (SQLException e) {
            ConnectionContext.rollback();
            throw new RuntimeException(e);
        }finally {
            ConnectionContext.clear();
        }
    }

}
