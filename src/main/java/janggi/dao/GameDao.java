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
import java.util.ArrayList;
import java.util.List;

public class GameDao {
    private static final DateTimeFormatter createdAtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<GameDao> gameDaos = new ArrayList<>();

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
                GameDao gameDao = new GameDao(resultSet.getInt("id"), game);
                gameDaos.add(gameDao);
                return gameDao;
            }
            throw new IllegalStateException("게임이 생성되지 않았습니다.");
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
        GameDao gameDao = new GameDao(gameDto.id(), game);
        gameDaos.add(gameDao);
        return gameDao;
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

    public static void updateTurn(Game game) {
        GameDao updatingGameDao = gameDaos.stream().
                filter(dao -> dao.game.equals(game))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        updatingGameDao.updateTurn();
    }

    private void updateTurn() {
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

    public static void deleteGame(Game game) {
        GameDao deletingGameDao = gameDaos.stream().
                filter(dao -> dao.game.equals(game))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        deletingGameDao.deleteGame();
    }

    private void deleteGame() {
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

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }
}
