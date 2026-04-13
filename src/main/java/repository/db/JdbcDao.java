package repository.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import repository.mapper.RowMapper;

public class JdbcDao {

    private final DataSource dataSource;

    public JdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long executeInsert(String sql, Object... parameters) {
        return execute(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setParameters(statement, parameters);
                statement.executeUpdate();
                return extractGeneratedKey(statement);
            }
        });
    }

    public void executeUpdate(String sql, Object... parameters) {
        execute(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                setParameters(statement, parameters);
                statement.executeUpdate();
                return null;
            }
        });
    }

    public <T> T executeQuery(String sql, RowMapper<T> mapper, Object... parameters) {
        return execute(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                setParameters(statement, parameters);
                try (ResultSet rs = statement.executeQuery()) {
                    return mapper.map(rs);
                }
            }
        });
    }

    public void executeBatch(String sql, List<Object[]> parameters) {
        execute(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                for (Object[] params : parameters) {
                    setParameters(statement, params);
                    statement.addBatch();
                }
                statement.executeBatch();
                return null;
            }
        });
    }

    private <T> T execute(PreparedStatementExecutor<T> executor) {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection(dataSource);
            return executor.execute(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionManager.releaseConnection(conn);
        }
    }

    private long extractGeneratedKey(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("[ERROR] ID를 생성할 수 없습니다.");
        }
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    @FunctionalInterface
    private interface PreparedStatementExecutor<T> {
        T execute(Connection conn) throws SQLException;
    }
}