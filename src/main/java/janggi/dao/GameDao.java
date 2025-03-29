package janggi.dao;

import janggi.game.Game;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameDao {
    private static final DateTimeFormatter createdAtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void createGame(final Game game) {
        final var query = "INSERT INTO game (turn,created_at) VALUES(?,?)";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, game.getTurn().name());
            preparedStatement.setString(2, game.getCreatedAt().format(createdAtFormatter));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Game findLastCreated() {
        //TODO piece로 보드도 함께 가져와야 함
        final var query = "SELECT * FROM game ORDER BY created_at DESC LIMIT 1;";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Game(
                        LocalDateTime.parse(resultSet.getString("created_at"), createdAtFormatter)
                );
            }
            throw new IllegalStateException("게임 기록이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
