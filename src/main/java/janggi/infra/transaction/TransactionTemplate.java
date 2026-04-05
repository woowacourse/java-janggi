package janggi.infra.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class TransactionTemplate {

    private final DataSource dataSource;

    public TransactionTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public  <T> T executeInTransaction(TransactionCallback<T> action) {
        try (Connection connection = dataSource.getConnection()) {
            return processTransaction(connection, action);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 커넥션 에러", e);
        }
    }

    private <T> T processTransaction(Connection connection, TransactionCallback<T> action) throws SQLException {
        try {
            connection.setAutoCommit(false);
            ConnectionContext.setConnection(connection);
            T result = action.doInTransaction();
            connection.commit();
            return result;
        } catch (RuntimeException e) {
            connection.rollback();
            throw e;
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("[ERROR] 게임 저장 중 트랜잭션 롤백됨", e);
        } finally {
            ConnectionContext.clear();
            connection.setAutoCommit(true);
        }
    }
}
