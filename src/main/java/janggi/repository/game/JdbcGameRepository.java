package janggi.repository.game;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import janggi.config.DatabaseManager;
import janggi.domain.game.GameState;
import janggi.entity.GameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {

    @Override
    public Long save(Connection connection, GameEntity game) {
        String sql = "INSERT INTO janggi_game (turn, state) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            statement.setString(1, game.currentTurn());
            statement.setString(2, game.gameState());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new RuntimeException("id 생성 실패");
                }
                return keys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패", e);
        }
    }

    @Override
    public void update(Connection connection, Long gameId, GameEntity game) {
        String sql = "UPDATE janggi_game SET turn = ?, state = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, game.currentTurn());
            statement.setString(2, game.gameState());
            statement.setLong(3, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("수정 실패", e);
        }
    }

    @Override
    public List<Long> findAllByState(GameState state) {
        String sql = "SELECT id FROM janggi_game WHERE state = ? ORDER BY id ASC";

        List<Long> gameIds = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, state.name());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    gameIds.add(resultSet.getLong("id"));
                }
            }
            return gameIds;
        } catch (SQLException e) {
            throw new RuntimeException("진행 중 게임 조회 실패", e);
        }
    }

    @Override
    public Optional<GameEntity> findById(Long gameId) {
        String sql = "SELECT turn, state FROM janggi_game WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                String currentTurn = resultSet.getString("turn");
                String gameState = resultSet.getString("state");
                return Optional.of(new GameEntity(currentTurn, gameState));
            }
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }
    }

}
