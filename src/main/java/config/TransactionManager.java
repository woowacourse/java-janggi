package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public class TransactionManager {
    public <T> T executeWrite(Function<Connection, T> action) {
        Connection conn = ConnectionManager.getConnection();

        try {
            conn.setAutoCommit(false);
            T result = action.apply(conn);

            conn.commit();
            return result;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.closeConnection(conn);
        }
    }

    public <T> T executeRead(Function<Connection, T> action) {
        Connection conn = ConnectionManager.getConnection();
        try {
            return action.apply(conn);
        } finally {
            ConnectionManager.closeConnection(conn);
        }
    }
}
