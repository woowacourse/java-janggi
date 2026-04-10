package global.template;

import infra.JDBCContext;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTemplate {
    public static <T> T execute(TransactionCallback<T> callback) {
        Connection connection = null;

        try {
            connection = JDBCContext.getConnection();
            connection.setAutoCommit(false);

            T result = callback.doInTransaction(connection);

            connection.commit();

            return result;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ignored) {
                }
            }
            throw new RuntimeException("트랜잭션 실패", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
    }
}
