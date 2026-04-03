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

    private final JdbcConnectionGenerator jdbcConnectionGenerator;
    private ResultSet resultSet = null;

    public JdbcTemplate(JdbcConnectionGenerator jdbcConnectionGenerator) {
        this.jdbcConnectionGenerator = jdbcConnectionGenerator;
    }

    public <T> List<T> executeRead(String sql, RowMapper<T> rowMapper, Object... parameters) throws SQLException {
        Connection connection = jdbcConnectionGenerator.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            for (int i = 1; i <= parameters.length; i++) {
                preparedStatement.setObject(i, parameters[i - 1]);
            }

            resultSet = preparedStatement.executeQuery();
            List<T> results = new ArrayList<>();

            while (resultSet.next()) {
                results.add(rowMapper.mapRow(resultSet));
            }

            return results;
        } finally {
            close(preparedStatement, connection, resultSet);
        }
    }

    public Object executeSave(String sql, Object... parameters) throws SQLException {
        try (Connection connection = jdbcConnectionGenerator.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            try {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }

                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getObject(1);

                    }
                }
            } finally {
                close(preparedStatement, connection, resultSet);
            }
        }
        throw new IllegalStateException(RepositoryErrorMessage.FAIL_SAVE_ENTITY.getMessage());
    }

    public List<Object> executeBatchSave(String sql, List<List<Object>> parameters) throws SQLException {
        List<Object> keys = new ArrayList<>();

        try (Connection connection = jdbcConnectionGenerator.getDBConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            try {
                for (List<Object> params : parameters) {
                    for (int i = 0; i < params.size(); i++) {
                        preparedStatement.setObject(i + 1, params.get(i));
                    }
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    while (generatedKeys.next()) {
                        keys.add(generatedKeys.getObject(1));
                    }
                }
            } finally {
                close(preparedStatement, connection, resultSet);
            }

            return keys;
        }
    }

    public int executeCommand(String sql, Object... parameters) throws SQLException {
        Connection connection = jdbcConnectionGenerator.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            for (int i = 1; i <= parameters.length; i++) {
                preparedStatement.setObject(i, parameters[i - 1]);
            }
            return preparedStatement.executeUpdate();
        } finally {
            close(preparedStatement, connection, resultSet);
        }
    }

    public void executeBatchCommand(String sql, List<List<Object>> parameterBatch) throws SQLException {
        Connection connection = jdbcConnectionGenerator.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        try {
            for (List<Object> params : parameterBatch) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } finally {
            close(preparedStatement, connection, resultSet);
        }
    }

    private static void close(PreparedStatement stmt, Connection con, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("rs close error" + e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("stmt close error" + e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("con close error" + e);
            }
        }
    }

    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}
