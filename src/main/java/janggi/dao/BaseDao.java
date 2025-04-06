package janggi.dao;

import janggi.infrastructure.DatabaseConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao {

    protected final DatabaseConnectionProvider databaseConnectionProvider;

    protected BaseDao(final DatabaseConnectionProvider databaseConnectionProvider) {
        this.databaseConnectionProvider = databaseConnectionProvider;
    }

    protected final <T> List<T> findAll(final String query, final StatementSetter setter,
                                        final ResultMapper<T> resultMapper) {
        final List<T> results = new ArrayList<>();
        try (final Connection connection = databaseConnectionProvider.getConnection();
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

    protected final <T> List<T> findAll(final String query, final ResultMapper<T> resultMapper) {
        return findAll(query, null, resultMapper);
    }

    protected final <T> T findOne(final String query, final StatementSetter setter,
                                  final ResultMapper<T> resultMapper) {
        try (final Connection connection = databaseConnectionProvider.getConnection();
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

    protected final <T> T findOne(final String query, final ResultMapper<T> resultMapper) {
        return findOne(query, null, resultMapper);
    }

    protected final void executeUpdate(final String query, final StatementSetter setter) {
        try (final Connection connection = databaseConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (setter != null) {
                setter.setParameters(preparedStatement, connection);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected final String getTeamNameById(final Connection connection, final int teamId) {
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

    protected final int getTeamIdByName(final Connection connection, final String teamName) {
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

    protected final void executeUpdate(final String query) {
        try (final var connection = databaseConnectionProvider.getConnection();
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
