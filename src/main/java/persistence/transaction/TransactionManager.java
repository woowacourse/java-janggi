package persistence.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import persistence.connector.DataBaseConnector;

public class TransactionManager {

    private final DataBaseConnector dataBaseConnector;

    public TransactionManager(DataBaseConnector dataBaseConnector) {
        this.dataBaseConnector = dataBaseConnector;
    }

    public void execute(SqlConsumer<Connection> consumer) {
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                consumer.accept(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 트랜잭션 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생", e);
        }
    }

    public <T> T executeQuery(SqlFunction<Connection, T> function) {
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = function.apply(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 트랜잭션 중 오류 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생", e);
        }
    }
}
