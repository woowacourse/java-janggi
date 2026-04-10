package janggi.infra.transaction;

import janggi.infra.util.ConnectionHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTemplate {

    private final DataSource dataSource;

    public TransactionTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(TransactionalCallback<T> action) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);

            ConnectionHolder.bind(con);

            T result = action.doInTransaction();

            con.commit();
            return result;
        } catch (SQLException e) {
            rollback(con);
            throw new RuntimeException(e);
        } finally {
            ConnectionHolder.remove();
            close(con);
        }
    }

    public void executeWithoutResult(Runnable action) {
        execute(() -> {
            action.run();
            return null;
        });
    }



    private static void rollback(Connection con) {
        if(con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void close(Connection con) {
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
