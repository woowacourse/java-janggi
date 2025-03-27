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

    public List<JanggiDto> findAllJanggiDtos(final Connection connection) {
        final var query = "SELECT * FROM janggi";
        try (final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();

            final List<JanggiDto> janggiDtos = new ArrayList<>();
            while (!resultSet.next()) {
                final JanggiDto janggiDto = new JanggiDto(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        new Turn(Team.from(resultSet.getString("turn"))),
                        JanggiStatus.from(resultSet.getString("status"))
                );
                janggiDtos.add(janggiDto);
            }

            return janggiDtos;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public JanggiDto findJanggiDtoById(
            final Connection connection,
            final int id
    ) {
        final var query = "SELECT * FROM janggi WHERE id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            final var resultSet = preparedStatement.executeQuery();

            return new JanggiDto(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    new Turn(Team.valueOf(resultSet.getString("turn"))),
                    JanggiStatus.from(resultSet.getString("status"))
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int create(
            final Connection connection,
            final String title,
            final JanggiStatus status,
            final Turn turn
    ) {
        final var query = "INSERT INTO janggi (title, status, turn) VALUES (?, ?, ?)";
        try {
            final var preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, status.name());
            preparedStatement.setString(3, turn.currentTeam().name());

            preparedStatement.executeUpdate();

            return (int) preparedStatement.getGeneratedKeys().getLong(1);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurnByJanggiId(
            final Connection connection,
            final int janggiId,
            final Team changedTeam
    ) {
        final var query = "UPDATE janggi SET turn = ? WHERE janggi_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, changedTeam.getTitle());
            preparedStatement.setInt(2, janggiId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
