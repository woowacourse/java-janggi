package service;

import db.jdbc.ConnectionManager;
import db.jdbc.SqlConnection;
import db.jdbc.SqlConnectionWrapper;
import java.sql.Connection;
import java.sql.SQLException;

public final class DbTemplate {

    private final ConnectionManager connectionManager;

    public DbTemplate(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public <T> T readOnly(SqlConnectionOperation<T> operation) {
        try (Connection connection = connectionManager.getConnection()) {
            return operation.execute(new SqlConnectionWrapper(connection));
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        } catch (Exception e) {
            throw new IllegalStateException("DB 조회에 실패했습니다.", e);
        }
    }

    public <T> T inTransaction(SqlConnectionOperation<T> operation) {
        try (Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = operation.execute(new SqlConnectionWrapper(connection));
                connection.commit();
                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new IllegalStateException("DB 작업에 실패했습니다.", e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }

    @FunctionalInterface
    public interface SqlConnectionOperation<T> {
        T execute(SqlConnection connection) throws Exception;
    }
}
