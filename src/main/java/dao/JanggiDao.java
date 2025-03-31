package dao;

import domain.janggi.JanggiStatus;
import domain.janggi.Team;
import domain.janggi.Turn;
import entity.JanggiEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JanggiDao {

    public int create(
            final Connection connection,
            final String title,
            final JanggiStatus status,
            final Turn turn
    ) throws SQLException {
        final String query = "INSERT INTO janggi (title, status, turn) VALUES (?, ?, ?)";
        final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, status.name());
        preparedStatement.setString(3, turn.currentTeam().name());

        preparedStatement.executeUpdate();
        final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return (int) generatedKeys.getLong(1);
        }
        throw new SQLException("생성된 키가 없습니다.");
    }

    public List<JanggiEntity> findAllJanggiEntities(final Connection connection) throws SQLException {
        final String query = "SELECT * FROM janggi";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);

        final ResultSet resultSet = preparedStatement.executeQuery();

        final List<JanggiEntity> janggiEntities = new ArrayList<>();
        while (resultSet.next()) {
            final JanggiEntity janggiEntity = new JanggiEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    Team.from(resultSet.getString("turn")),
                    JanggiStatus.from(resultSet.getString("status"))
            );
            janggiEntities.add(janggiEntity);
        }

        return janggiEntities;
    }

    public JanggiEntity findJanggiEntityById(
            final Connection connection,
            final int id
    ) throws SQLException {
        final String query = "SELECT * FROM janggi WHERE id = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        final ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new JanggiEntity(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    Team.from(resultSet.getString("turn")),
                    JanggiStatus.from(resultSet.getString("status"))
            );
        }
        throw new SQLException("해당하는 장기 게임이 없습니다.");
    }

    public void updateTurnByJanggiId(
            final Connection connection,
            final int janggiId,
            final Team changedTeam
    ) throws SQLException {
        final String query = "UPDATE janggi SET turn = ? WHERE id = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, changedTeam.name());
        preparedStatement.setInt(2, janggiId);
        preparedStatement.executeUpdate();
    }

    public void deleteAll(final Connection connection) throws SQLException {
        final String query = "DELETE FROM janggi WHERE TRUE";
        final PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
