package repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import repository.RepositoryErrorMessage;

public class JdbcTemplate {

    public <T> List<T> executeRead(Connection connection, String sql, RowMapper<T> rowMapper, Object... parameters) {
        return execute(connection, sql, false, ps -> {
            setParameters(ps, parameters);
            try (ResultSet resultSet = ps.executeQuery()) {
                List<T> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(rowMapper.mapRow(resultSet));
                }
                return results;
            }
        });
    }

    public Object executeSave(Connection connection, String sql, Object... parameters) {
        return execute(
                connection, sql, true, ps -> {
                    setParameters(ps, parameters);
                    ps.executeUpdate();
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            return generatedKeys.getObject(1);
                        }
                    }
                    throw new IllegalStateException(RepositoryErrorMessage.FAIL_SAVE_ENTITY.getMessage());
                }
        );
    }

    public List<Object> executeBatchSave(Connection connection, String sql, List<List<Object>> parameters) {
        return execute(
                connection, sql, true, ps -> {
                    setBatchParameters(ps, parameters);
                    ps.executeBatch();
                    List<Object> keys = new ArrayList<>();
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        while (generatedKeys.next()) {
                            keys.add(generatedKeys.getObject(1));
                        }
                    }
                    return keys;
                }
        );
    }

    public int executeCommand(Connection connection, String sql, Object... parameters) {
        return execute(
                connection, sql, false, ps -> {
                    setParameters(ps, parameters);
                    return ps.executeUpdate();
                }
        );
    }

    public void executeBatchCommand(Connection connection, String sql, List<List<Object>> parameterBatch) {
        execute(
                connection, sql, false, ps -> {
                    setBatchParameters(ps, parameterBatch);
                    ps.executeBatch();
                    return null;
                }
        );
    }

    private interface PreparedStatementCallback<T> {
        T doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException;
    }

    public interface RowMapper<T> {
        T mapRow(ResultSet resultSet) throws SQLException;
    }

    private <T> T execute(Connection connection, String sql, boolean returnGeneratedKeys,
                          PreparedStatementCallback<T> action) {
        try (PreparedStatement preparedStatement = getPreparedStatement(connection, sql, returnGeneratedKeys)) {
            return action.doInPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            throw new IllegalStateException(RepositoryErrorMessage.SQL_EXCEPTION.getMessage(), e);
        }
    }

    private PreparedStatement getPreparedStatement(Connection connection, String sql,
                                                   boolean returnGeneratedKeys) throws SQLException {
        if (returnGeneratedKeys) {
            return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        return connection.prepareStatement(sql);
    }

    private void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }

    private void setBatchParameters(PreparedStatement preparedStatement, List<List<Object>> parameterBatch)
            throws SQLException {
        for (List<Object> params : parameterBatch) {
            setParameters(preparedStatement, params.toArray());
            preparedStatement.addBatch();
        }
    }
}
