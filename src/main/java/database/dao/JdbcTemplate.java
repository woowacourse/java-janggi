package database.dao;

import database.connection.ConnectionContext;
import database.dto.IntersectionDto;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplate {

    public Long save(String sql) throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        }
        return null;
    }

    // TODO board 저장 한정 추상화되지 않은 saveAll
    public void saveAll(String sql, Long boardId, List<IntersectionDto> intersections) throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        for (IntersectionDto dto : intersections) {
            setParameters(
                    preparedStatement,
                    boardId, dto.y(), dto.x(), dto.pieceType(), dto.teamName(), dto.intersectionType()
            );
            preparedStatement.addBatch();
        }

        preparedStatement.executeBatch();
    }

    public <T> T selectOne(String sql, RowMapper<T> mapper, Object... parameters) throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setParameters(preparedStatement, parameters);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return mapper.map(resultSet);
        }
        return null;
    }

    public <T> List<T> selectList(String sql, RowMapper<T> mapper, Object... parameters) throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setParameters(preparedStatement, parameters);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> list = new LinkedList<>();
        while (resultSet.next()) {
            list.add(mapper.map(resultSet));
        }
        return list;
    }

    public <T> List<T> selectList(String sql, RowMapper<T> mapper) throws SQLException {
        Connection connection = ConnectionContext.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> list = new LinkedList<>();
        while (resultSet.next()) {
            list.add(mapper.map(resultSet));
        }
        return list;
    }

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
