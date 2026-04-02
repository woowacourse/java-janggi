package repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    private final JdbcConnectionGenerator jdbcConnectionGenerator;

    public JdbcTemplate(JdbcConnectionGenerator jdbcConnectionGenerator) {
        this.jdbcConnectionGenerator = jdbcConnectionGenerator;
    }

    public ResultSet read(String sql, Object... parameters) throws SQLException {
        Connection connection = jdbcConnectionGenerator.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 1; i <= parameters.length; i++) {
            preparedStatement.setObject(i, parameters[i - 1]);
        }

        return preparedStatement.executeQuery();
    }

    public int command(String sql, Object... parameters) throws SQLException {
        Connection connection = jdbcConnectionGenerator.getDBConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (int i = 1; i <= parameters.length; i++) {
            preparedStatement.setObject(i, parameters[i - 1]);
        }

        return preparedStatement.executeUpdate();
    }
}
