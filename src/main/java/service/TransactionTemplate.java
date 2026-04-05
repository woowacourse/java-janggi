package service;

import java.sql.Connection;
import java.sql.SQLException;
import repository.jdbc.JdbcConnectionGenerator;

public class TransactionTemplate {
    private final JdbcConnectionGenerator connectionGenerator;

    public TransactionTemplate(JdbcConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    public <T> T execute(TransactionStrategy<T> strategy) {
        try (Connection connection = connectionGenerator.getDBConnection()) {
            connection.setAutoCommit(false);
            try {
                T result = strategy.doTransaction(connection);
                connection.commit();
                return result;
            } catch (Exception e) {
                connection.rollback();
                throw new IllegalStateException("비즈니스 로직 오류로 인한 롤백", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결에 실패하였습니다.");
        }
    }
}
