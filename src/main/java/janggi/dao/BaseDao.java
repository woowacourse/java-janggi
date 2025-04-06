package janggi.dao;

import janggi.utils.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao {

    private static final DBUtil dbUtil = DBUtil.getInstance();

    protected String getTeamNameById(final Connection connection, final int teamId) {
        final var query = "SELECT name FROM team WHERE team_id = ?";
        try (var preparedStatement = connection.prepareStatement(query)) {
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
        try (var preparedStatement = connection.prepareStatement(query)) {
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
        try (var connection = dbUtil.getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
