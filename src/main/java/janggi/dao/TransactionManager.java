package janggi.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public class TransactionManager {
    private final JdbcDataSource dataSource;

    public TransactionManager(JdbcDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(Supplier<T> logic) {
        if (ConnectionHolder.get() != null) {
            return logic.get();
        }

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            ConnectionHolder.set(conn);

            T ret = logic.get();
            conn.commit();
            
            return ret;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new IllegalStateException(ex);
                }
            }
            throw new IllegalStateException(e);

        } finally {
            ConnectionHolder.remove();
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }


    public void execute(Runnable logic) {
        if (ConnectionHolder.get() != null) {
            logic.run();
            return;
        }

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            ConnectionHolder.set(conn);

            logic.run();

            conn.commit();

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new IllegalStateException(ex);
                }
            }
            throw new IllegalStateException(e);

        } finally {
            ConnectionHolder.remove();
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }
}
