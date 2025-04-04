package persistence.dao;

import persistence.entity.JanggiGameEntity;
import domain.game.Turn;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class JanggiGameDao {

    public Long create(Connection connection, JanggiGameEntity entity) throws SQLException {
        final var createQuery = """
                INSERT INTO janggi_game (turn) VALUES (?)
                """;
        try (final var preparedStatement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.turn().getTeam().name());
            int rowCreated = preparedStatement.executeUpdate();
            if (rowCreated == 0) {
                throw new SQLException("[ERROR] 게임 생성에 실패하였습니다");
            }
            try (final var generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                throw new SQLException("[ERROR] id 생성을 실패하였습니다");
            }
        }
    }

    public void update(Connection connection, Long janggiGameId, JanggiGameEntity entity) throws SQLException{
        final var updateQuery = """
                UPDATE janggi_game SET turn = ? WHERE id = ?;
                """;
        try (final var preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, entity.turn().getTeam().name());
            preparedStatement.setLong(2, janggiGameId);
            int rowUpdated = preparedStatement.executeUpdate();
            if (rowUpdated == 0) {
                throw new SQLException("[ERROR] 게임 생성에 실패하였습니다");
            }
        }
    }

    public Optional<JanggiGameEntity> findById(Connection connection, Long id) throws SQLException {
        final var findByIdQuery = "SELECT * FROM janggi_game WHERE id = ?";

        try (final var preparedStatement = connection.prepareStatement(findByIdQuery)) {
            preparedStatement.setLong(1, id);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new JanggiGameEntity(
                        resultSet.getLong("id"),
                        new Turn(Team.valueOf(resultSet.getString("turn")))
                        )
                );
            }
        }
        return Optional.empty();
    }
}
