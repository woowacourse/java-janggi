package database.dao;

import database.connection.ConnectionContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class JdbcTemplate {

    public void update(String sql, Object... parameters) throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setParameters(preparedStatement, parameters);

        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("update를 수행할 행을 찾지 못했습니다.");
        }

    }

    public void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }

}
