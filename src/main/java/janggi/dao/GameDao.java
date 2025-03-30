package janggi.dao;

import janggi.dto.GameDto;
import janggi.dto.PieceDtos;
import janggi.game.Board;
import janggi.game.Game;
import janggi.game.Team;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameDao {
    private static final DateTimeFormatter createdAtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final int id;
    private Game game;

    public GameDao(int id, Game game) {
        this.id = id;
        this.game = game;
    }

    public static GameDao createGame(final Game game) {
        final var createQuery = "INSERT INTO game (turn,created_at) VALUES(?,?)";
        final var checkQuery = "SELECT * FROM game WHERE created_at=?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedCreateStatement = connection.prepareStatement(createQuery);
             final var preparedCheckStatement = connection.prepareStatement(checkQuery)) {
            preparedCreateStatement.setString(1, game.getTurn().name());
            preparedCreateStatement.setString(2, game.getCreatedAt().format(createdAtFormatter));
            preparedCreateStatement.executeUpdate();

            preparedCheckStatement.setString(1, game.getCreatedAt().format(createdAtFormatter));
            ResultSet resultSet = preparedCheckStatement.executeQuery();
            if (resultSet.next()) {
                return new GameDao(resultSet.getInt("id"), game);
            }
            throw new IllegalStateException("게임이 생성되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameDto findLastCreated() {
        final var gameQuery = "SELECT * FROM game ORDER BY created_at DESC LIMIT 1;";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedGameStatement = connection.prepareStatement(gameQuery)) {
            final var gameResultSet = preparedGameStatement.executeQuery();
            if (gameResultSet.next()) {
                return new GameDto(
                        gameResultSet.getInt("id"),
                        Team.valueOf(gameResultSet.getString("turn")),
                        LocalDateTime.parse(
                                gameResultSet.getString("created_at"), createdAtFormatter)
                );
            }
            throw new IllegalStateException("게임 기록이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameDao recreateGameFrom(PieceDtos pieceDtos, GameDto gameDto) {
        Game game = new Game(
                new Board(pieceDtos.getRunningPieces()),
                pieceDtos.getAttackedPieces(),
                gameDto.turn(),
                gameDto.createdAt()
        );
        return new GameDao(gameDto.id(), game);
    }

    public void deleteGame() {
        final var query = "DELETE FROM game WHERE id = ?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, this.id);
            int affectedCount = preparedStatement.executeUpdate();
            if (affectedCount == 0) {
                throw new IllegalStateException("게임이 삭제되지 않았습니다.");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn() {
        final var query = "UPDATE game SET turn=? WHERE id = ?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, game.getTurn().name());
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getId() {
        return id;
    }
}
