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
        return execute(connection, sql, false, ps -> processQuery(ps, rowMapper, parameters));
    }

    public Object executeSave(Connection connection, String sql, Object... parameters) {
        return execute(connection, sql, true, ps -> processSave(ps, parameters));
    }

    public List<Object> executeBatchSave(Connection connection, String sql, List<List<Object>> parameters) {
        return execute(connection, sql, true, ps -> processBatchSave(ps, parameters));
    }

    public int executeCommand(Connection connection, String sql, Object... parameters) {
        return execute(connection, sql, false, ps -> processUpdate(ps, parameters));
    }

    public interface RowMapper<T> {
        T mapRow(ResultSet resultSet) throws SQLException;
    }

    @FunctionalInterface
    private interface PreparedStatementCallback<T> {
        T doInPreparedStatement(PreparedStatement ps) throws SQLException;
    }

    private <T> T execute(Connection connection, String sql, boolean returnGeneratedKeys,
                          PreparedStatementCallback<T> action) {
        try (PreparedStatement ps = prepareStatement(connection, sql, returnGeneratedKeys)) {
            return action.doInPreparedStatement(ps);
        } catch (SQLException e) {
            throw new IllegalStateException(RepositoryErrorMessage.SQL_EXCEPTION.getMessage(), e);
        }
    }

    private <T> List<T> processQuery(PreparedStatement ps, RowMapper<T> rowMapper, Object[] parameters)
            throws SQLException {
        bindParameters(ps, parameters);
        try (ResultSet rs = ps.executeQuery()) {
            return mapResultSetToList(rs, rowMapper);
        }
    }

    private Object processSave(PreparedStatement ps, Object[] parameters) throws SQLException {
        bindParameters(ps, parameters);
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            return retrieveGeneratedKey(rs);
        }
    }

    private List<Object> processBatchSave(PreparedStatement ps, List<List<Object>> parameters)
            throws SQLException {
        bindBatchParameters(ps, parameters);
        ps.executeBatch();
        try (ResultSet rs = ps.getGeneratedKeys()) {
            return retrieveGeneratedKeys(rs);
        }
    }

    private int processUpdate(PreparedStatement ps, Object[] parameters) throws SQLException {
        bindParameters(ps, parameters);
        return ps.executeUpdate();
    }

    private PreparedStatement prepareStatement(Connection conn, String sql, boolean returnKeys) throws SQLException {
        if (returnKeys) {
            return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        }
        return conn.prepareStatement(sql);
    }

    private void bindParameters(PreparedStatement ps, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            ps.setObject(i + 1, parameters[i]);
        }
    }

    private void bindBatchParameters(PreparedStatement ps, List<List<Object>> parameterBatch) throws SQLException {
        for (List<Object> params : parameterBatch) {
            bindParameters(ps, params.toArray());
            ps.addBatch();
        }
    }

    private <T> List<T> mapResultSetToList(ResultSet rs, RowMapper<T> rowMapper) throws SQLException {
        List<T> results = new ArrayList<>();
        while (rs.next()) {
            results.add(rowMapper.mapRow(rs));
        }
        return results;
    }

    private Object retrieveGeneratedKey(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rs.getObject(1);
        }
        throw new IllegalStateException(RepositoryErrorMessage.FAIL_SAVE_ENTITY.getMessage());
    }

    private List<Object> retrieveGeneratedKeys(ResultSet rs) throws SQLException {
        List<Object> keys = new ArrayList<>();
        while (rs.next()) {
            keys.add(rs.getObject(1));
        }
        return keys;
    }
}
