package janggi.infra;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTemplate {

    private final DataConnectionManager manager;

    public TransactionTemplate(DataConnectionManager manager) {
        this.manager = manager;
    }

    public  <T> T executeInTransaction(TransactionCallback<T> action) {
        try (Connection connection = manager.getConnection()) {
            return processTransaction(connection, action);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 커넥션 에러", e);
        }
    }

    public void executeInTransaction(VoidTransactionCallback action) {
        try (Connection connection = manager.getConnection()) {
            processTransaction(connection, action);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 커넥션 에러", e);
        }
    }

    private <T> T processTransaction(Connection connection, TransactionCallback<T> action) throws SQLException {
        try {
            connection.setAutoCommit(false);
            T result = action.doInTransaction(connection);
            connection.commit();
            return result;
        } catch (RuntimeException e) {
            connection.rollback();
            throw e;
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("[ERROR] 게임 저장 중 트랜잭션 롤백됨", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void processTransaction(Connection connection, VoidTransactionCallback action) throws SQLException {
        try {
            connection.setAutoCommit(false);
            action.doInTransaction(connection);
            connection.commit();
        } catch (RuntimeException e) {
            connection.rollback();
            throw e;
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("[ERROR] 게임 저장 중 트랜잭션 롤백됨", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
