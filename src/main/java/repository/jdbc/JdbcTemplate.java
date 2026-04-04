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
    /**
     * Statement 실행을 위한 콜백 인터페이스
     */
    private interface PreparedStatementCallback<T> {
        T doInPreparedStatement(PreparedStatement ps) throws SQLException;
    }

    /**
     * 자원 할당/해제를 책임지는 기본 실행 메서드 (try-with-resources 활용)
     */
    private <T> T execute(Connection connection, String sql, boolean returnGeneratedKeys,
                          PreparedStatementCallback<T> action)
            throws SQLException {
        try (PreparedStatement preparedStatement = returnGeneratedKeys ?
                connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) :
                connection.prepareStatement(sql)) {
            return action.doInPreparedStatement(preparedStatement);
        }
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

    public <T> List<T> executeRead(Connection connection, String sql, RowMapper<T> rowMapper, Object... parameters)
            throws SQLException {
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

    public Object executeSave(Connection connection, String sql, Object... parameters) throws SQLException {
        return execute(connection, sql, true, ps -> {
            setParameters(ps, parameters);
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getObject(1);
                }
            }
            throw new IllegalStateException(RepositoryErrorMessage.FAIL_SAVE_ENTITY.getMessage());
        });
    }

    public List<Object> executeBatchSave(Connection connection, String sql, List<List<Object>> parameters)
            throws SQLException {
        return execute(connection, sql, true, ps -> {
            setBatchParameters(ps, parameters);
            ps.executeBatch();
            List<Object> keys = new ArrayList<>();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    keys.add(generatedKeys.getObject(1));
                }
            }
            return keys;
        });
    }

    public int executeCommand(Connection connection, String sql, Object... parameters) throws SQLException {
        return execute(connection, sql, false, ps -> {
            setParameters(ps, parameters);
            return ps.executeUpdate();
        });
    }

    public void executeBatchCommand(Connection connection, String sql, List<List<Object>> parameterBatch)
            throws SQLException {
        execute(connection, sql, false, ps -> {
            setBatchParameters(ps, parameterBatch);
            ps.executeBatch();
            return null;
        });
    }

    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}
