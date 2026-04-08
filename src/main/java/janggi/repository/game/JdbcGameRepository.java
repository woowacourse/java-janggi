package janggi.repository.game;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import janggi.config.DatabaseManager;
import janggi.domain.game.GameState;
import janggi.entity.TurnEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {

    @Override
    public Long save(Connection connection, TurnEntity turn) {
        String gameSql = "INSERT INTO janggi_game (turn, state) VALUES (?, ?)";

        try (PreparedStatement gameStatement = connection.prepareStatement(gameSql, RETURN_GENERATED_KEYS)) {
            gameStatement.setString(1, turn.currentTurn());
            gameStatement.setString(2, GameState.PLAYING.name());
            gameStatement.executeUpdate();

            ResultSet keys = gameStatement.getGeneratedKeys();
            if (!keys.next()) {
                throw new RuntimeException("id 생성 실패");
            }
            return keys.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("저장 실패", e);
        }
    }

    @Override
    public Optional<TurnEntity> findByCurrentTurnById(Long gameId) {
        String gameSql = "SELECT turn FROM janggi_game WHERE id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(gameSql)) {
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                String currentTurn = resultSet.getString("turn");
                return Optional.of(TurnEntity.toEntity(currentTurn));
            }
        } catch (SQLException e) {
            throw new RuntimeException("조회 실패", e);
        }
    }

    @Override
    public void updateTurn(Connection connection, Long gameId, TurnEntity game) {
        String updateGameSql = "UPDATE janggi_game SET turn = ? WHERE id = ?";

        try (PreparedStatement updateGameStatement = connection.prepareStatement(updateGameSql)) {
            updateGameStatement.setString(1, game.currentTurn());
            updateGameStatement.setLong(2, gameId);
            updateGameStatement.executeUpdate();
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
    public void updateState(Connection connection, Long gameId, GameState state) {
        String sql = "UPDATE janggi_game SET state = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, state.name());
            statement.setLong(2, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("게임 상태 수정 실패", e);
        }
    }

}
