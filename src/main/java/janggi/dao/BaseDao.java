package janggi.dao;

import janggi.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao {

    private static final DBUtil dbUtil = DBUtil.getInstance();

    protected <T> List<T> executeQueryWithMultiData(final String query, final StatementSetter setter,
                                                    final ResultMapper<T> resultMapper) {
        final List<T> results = new ArrayList<>();
        try (final Connection connection = dbUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (setter != null) {
                setter.setParameters(preparedStatement, connection);
            }

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                results.add(resultMapper.mapRow(resultSet, connection));
            }
            return results;

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> List<T> executeQueryWithMultiData(final String query, final ResultMapper<T> resultMapper) {
        return executeQueryWithMultiData(query, null, resultMapper);
    }

    protected <T> T executeQuery(final String query, final StatementSetter setter,
                                 final ResultMapper<T> resultMapper) {
        try (final Connection connection = dbUtil.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (setter != null) {
                setter.setParameters(preparedStatement, connection);
            }

            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultMapper.mapRow(resultSet, connection);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] 해당 데이터를 찾을 수 없습니다.");
    }

    protected <T> T executeQuery(final String query, final ResultMapper<T> resultMapper) {
        return executeQuery(query, null, resultMapper);
    }

    protected void executeUpdate(final String query, final StatementSetter setter) {
        try (final Connection connection = dbUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (setter != null) {
                setter.setParameters(preparedStatement, connection);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getTeamNameById(final Connection connection, final int teamId) {
        final var query = "SELECT name FROM team WHERE team_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] Team을 찾을 수 없습니다.");
    }

    protected int getTeamIdByName(final Connection connection, final String teamName) {
        final var query = "SELECT * FROM team WHERE name = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teamName);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("team_id");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] Team을 찾을 수 없습니다.");
    }

    protected void executeUpdate(final String query) {
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    protected interface StatementSetter {
        void setParameters(PreparedStatement preparedStatement, Connection connection) throws SQLException;
    }

    @FunctionalInterface
    protected interface ResultMapper<T> {
        T mapRow(ResultSet resultSet, Connection connection) throws SQLException;
    }
}
