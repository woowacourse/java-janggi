package repository;

import repository.mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class JdbcTemplate {
    private static final String ERROR_SQL = "SQL 처리에 문제가 발생했습니다";
    private final Connection connection;

    public JdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    public void executeInTransaction(Runnable task) {
        setAutoCommit(false);
        try {
            task.run();
            commit();
        } catch (IllegalStateException e) {
            rollback();
            throw new IllegalStateException(ERROR_SQL, e);
        } finally {
            setAutoCommit(true);
        }
    }

    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SQL, e);
        }
    }

    private void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SQL, e);
        }
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SQL, e);
        }
    }

    public <T> T executeInTransaction(Callable<T> task) {
        setAutoCommit(false);
        try {
            T result = task.call();
            commit();
            return result;
        } catch (Exception e) {
            rollback();
            throw new IllegalStateException(ERROR_SQL, e);
        } finally {
            setAutoCommit(true);
        }
    }

    public Long executeAndReturnKey(String sql, PreparedStatementSetter setter) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setter.setValues(preparedStatement);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (!resultSet.next()) {
                throw new IllegalStateException("해당 레코드가 존재하지 않습니다.");
            }
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SQL, e);
        }
    }

    public <T> Optional<T> queryForSingleObject(String sql, RowMapper<T> mapper) {
        return queryForSingleObject(sql, stmt -> {
        }, mapper);
    }

    public <T> Optional<T> queryForSingleObject(String sql, PreparedStatementSetter setter, RowMapper<T> mapper) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setter.setValues(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(mapper.mapRow(rs));
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SQL, e);
        }
    }

    public <T> List<T> query(String sql, PreparedStatementSetter setter, RowMapper<T> mapper) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setter.setValues(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapper.mapRow(rs));
            }
            return results;
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SQL, e);
        }
    }

    public void execute(String sql, PreparedStatementSetter setter) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setter.setValues(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(ERROR_SQL, e);
        }
    }
}
