package janggi.dao;

import janggi.dto.GameDto;
import janggi.exception.GameNotDeletedException;
import janggi.game.Game;
import janggi.game.Team;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class GameDao {
    private static final DateTimeFormatter createdAtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void createGame(final Game game) {
        final var createQuery = "INSERT INTO game (turn,created_at) VALUES(?,?)";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedCreateStatement = connection.prepareStatement(createQuery)) {
            preparedCreateStatement.setString(1, game.getTurn().name());
            preparedCreateStatement.setString(2, game.getCreatedAt().format(createdAtFormatter));
            preparedCreateStatement.executeUpdate();

            GameRecord.addRecord(findCreatedGameId(game), game);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int findCreatedGameId(Game game) {
        final var checkQuery = "SELECT * FROM game WHERE created_at=?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedCheckStatement = connection.prepareStatement(checkQuery)) {
            preparedCheckStatement.setString(1, game.getCreatedAt().format(createdAtFormatter));
            ResultSet resultSet = preparedCheckStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new IllegalStateException("게임이 생성되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameDto findLastCreated() {
        final var gameQuery = "SELECT * FROM game ORDER BY id DESC LIMIT 1;";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedGameStatement = connection.prepareStatement(gameQuery)) {
            final var gameResultSet = preparedGameStatement.executeQuery();
            if (gameResultSet.next()) {
                return new GameDto(
                        gameResultSet.getInt("id"),
                        Team.valueOf(gameResultSet.getString("turn")),
                        LocalDateTime.parse(gameResultSet.getString("created_at"), createdAtFormatter)
                );
            }
            throw new IllegalStateException("게임 기록이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTurn(Game game) {
        final var query = "UPDATE game SET turn=? WHERE id = ?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)){
            GameRecord gameRecord = GameRecord.findByGame(game);
            preparedStatement.setString(1, gameRecord.getTurn());
            preparedStatement.setInt(2, gameRecord.getId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteGame(Game game) {
        final var query = "DELETE FROM game WHERE id = ?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)){
            GameRecord gameRecord = GameRecord.findByGame(game);
            preparedStatement.setInt(1, gameRecord.getId());
            int affectedCount = preparedStatement.executeUpdate();
            if (affectedCount == 0) {
                throw new GameNotDeletedException();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
