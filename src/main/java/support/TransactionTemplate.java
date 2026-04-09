package support;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class TransactionTemplate {

    private static final Logger LOGGER = Logger.getLogger(TransactionTemplate.class.getName());
    private static final String LOG_MESSAGE_FORMAT = "%s 중 예외 발생";

    private final DataSource dataSource;

    public TransactionTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(TransactionCallback<T> action) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            T doingResult = action.doInTransaction(conn);

            conn.commit();

            return doingResult;
        } catch (SQLException e) {
            rollback(conn);
            throw new DataAccessException(e);
        } finally {
            close(conn);
        }
    }

    private void rollback(Connection conn) {
        if (conn == null) {
            return;
        }

        try {
            conn.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, LOG_MESSAGE_FORMAT.formatted("롤백"), e);
        }
    }

    private void close(Connection conn) {
        if (conn == null) {
            return;
        }

        try {
            conn.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, LOG_MESSAGE_FORMAT.formatted("Connection 반납"), e);
        }
    }
}
