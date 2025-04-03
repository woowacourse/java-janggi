package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor {

    private final DatabaseConnection databaseConnection;

    public Executor(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void execute(String query, SQLConsumer<PreparedStatement> preparer,
                        SQLConsumer<PreparedStatement> executor) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            preparer.accept(statement);
            executor.accept(statement);
        } catch (SQLException e) {
            throw new RuntimeException("SQL 실행 실패", e);
        }
    }

    public void executeUpdate(String query, SQLConsumer<PreparedStatement> preparer) {
        execute(query, preparer, PreparedStatement::executeUpdate);
    }


    public void executeBatch(String query, SQLConsumer<PreparedStatement> preparer) {
        execute(query, preparer, PreparedStatement::executeBatch);
    }

    public void executeTransaction(SQLConsumer<Connection> transaction) {
        try (Connection connection = databaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                transaction.accept(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("트랜잭션 롤백됨", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("트랜잭션 시작 실패", e);
        }
    }

    public <T> T executeQuery(String query, SQLFunction<PreparedStatement, ResultSet, T> handler) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            return handler.apply(statement, resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("쿼리 조회 실패", e);
        }
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLFunction<T, U, R> {
        R apply(T t, U u) throws SQLException;
    }
}
