package dao;

import domain.janggi.JanggiStatus;
import domain.janggi.Team;
import domain.janggi.Turn;
import dto.JanggiDto;
import java.sql.Connection;
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
        final var query = "INSERT INTO janggi (title, status, turn) VALUES (?, ?, ?)";
        final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, status.name());
        preparedStatement.setString(3, turn.currentTeam().name());

        preparedStatement.executeUpdate();

        final var generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return (int) generatedKeys.getLong(1);
        }
        throw new SQLException("생성된 키가 없습니다.");
    }

    public List<JanggiDto> findAllJanggiDtos(final Connection connection) throws SQLException {
        final var query = "SELECT * FROM janggi";
        final var preparedStatement = connection.prepareStatement(query);

        final var resultSet = preparedStatement.executeQuery();

        final List<JanggiDto> janggiDtos = new ArrayList<>();
        while (resultSet.next()) {
            final JanggiDto janggiDto = new JanggiDto(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    new Turn(Team.from(resultSet.getString("turn"))),
                    JanggiStatus.from(resultSet.getString("status"))
            );
            janggiDtos.add(janggiDto);
        }

        return janggiDtos;

    }

    public JanggiDto findJanggiDtoById(
            final Connection connection,
            final int id
    ) throws SQLException {
        final var query = "SELECT * FROM janggi WHERE id = ?";
        final var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        final var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new JanggiDto(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    new Turn(Team.from(resultSet.getString("turn"))),
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
        final var query = "UPDATE janggi SET turn = ? WHERE id = ?";
        final var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, changedTeam.name());
        preparedStatement.setInt(2, janggiId);
        preparedStatement.executeUpdate();
    }

    public void deleteAll(final Connection connection) throws SQLException {
        final var query = "DELETE FROM janggi WHERE TRUE";
        final var preparedStatement = connection.prepareStatement(query);
        preparedStatement.executeUpdate();
    }
}
