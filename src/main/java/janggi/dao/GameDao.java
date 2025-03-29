package janggi.dao;

import janggi.game.Game;
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

    public static GameDao findLastCreated() {
        final var query = "SELECT * FROM game ORDER BY created_at DESC LIMIT 1;";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new GameDao(
                        resultSet.getInt("id"),
                        new Game(LocalDateTime.parse(
                                resultSet.getString("created_at"), createdAtFormatter)
                        )
                );
                //TODO piece도 반영
            }
            throw new IllegalStateException("게임 기록이 존재하지 않습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
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

    public int getId() {
        return id;
    }
}
