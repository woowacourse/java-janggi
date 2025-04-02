package janggi.db;

import janggi.exception.DataAccessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

public class TransactionManager {

    private final Connection connection;

    public TransactionManager(Connection connection) {
        this.connection = connection;
    }

    public <T> T performTransaction(Supplier<T> operation) {
        try {
            connection.setAutoCommit(false);
            T result = operation.get();
            connection.commit();
            return result;
        } catch (Exception e) {
            rollbackQuietly();
            throw new DataAccessException("트랜잭션 수행 중 오류가 발생했습니다.", e);
        } finally {
            resetAutoCommitQuietly();
        }
    }

    private void rollbackQuietly() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.err.println("롤백 실패: " + e.getMessage());
        }
    }

    private void resetAutoCommitQuietly() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("AutoCommit 복원 실패: " + e.getMessage());
        }
    }
}
