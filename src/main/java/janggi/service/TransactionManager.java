package janggi.service;

import janggi.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public void execute(Runnable action) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            run(connection, action);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 연결에 실패했습니다.", e);
        }
    }

    private void run(Connection connection, Runnable action) throws SQLException {
        connectionHolder.set(connection);
        connection.setAutoCommit(false);
        try {
            action.run();
            connection.commit();
        } catch (IllegalArgumentException | IllegalStateException e) {
            connection.rollback();
            throw e;
        } catch (RuntimeException e) {
            connection.rollback();
            throw new IllegalStateException("트랜잭션 처리 중 오류가 발생했습니다.", e);
        } finally {
            connectionHolder.remove();
        }
    }

    public Connection getConnection() {
        return connectionHolder.get();
    }
}
