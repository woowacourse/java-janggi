package db.dao;

import core.GameStatus;
import db.jdbc.SqlConnection;
import db.model.GameEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import participant.Turn;

public class JdbcGameDao implements GameDao {

    @Override
    public Long save(final SqlConnection connection, final GameEntity gameEntity) {
        final String sql = """
            INSERT INTO game (turn, status, created_at, updated_at)
            VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, gameEntity.turn().name());
            statement.setString(2, gameEntity.status().name());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
            throw new IllegalStateException("게임 저장 후 생성된 ID를 조회할 수 없습니다.");
        } catch (SQLException e) {
            throw new IllegalStateException("게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<GameEntity> findById(final SqlConnection connection, final Long id) {
        if (id == null) {
            throw new IllegalStateException("조회할 게임 ID가 필요합니다.");
        }

        final String sql = """
            SELECT id, turn, status
            FROM game
            WHERE id = ?
            """;
        try (final PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (final ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(parseGame(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public List<GameEntity> findTop10OrderByCreatedAtDesc(final SqlConnection connection) {
        final String sql = """
            SELECT id, turn, status
            FROM game
            ORDER BY created_at DESC
            LIMIT 10
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql);
             final ResultSet resultSet = statement.executeQuery()) {

            final List<GameEntity> gameEntities = new ArrayList<>();
            while (resultSet.next()) {
                gameEntities.add(parseGame(resultSet));
            }
            return gameEntities;
        } catch (SQLException e) {
            throw new IllegalStateException("게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void updateState(final SqlConnection connection, final Long id, final Turn turn, final GameStatus gameStatus) {
        if (id == null) {
            throw new IllegalArgumentException("수정할 게임 ID가 필요합니다.");
        }

        final String sql = """
            UPDATE game
            SET turn = ?, status = ?, updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;

        try (final PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, turn.name());
            statement.setString(2, gameStatus.name());
            statement.setLong(3, id);

            final int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("수정할 게임이 존재하지 않습니다. id=" + id);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 수정에 실패했습니다.", e);
        }
    }

    private GameEntity parseGame(final ResultSet resultSet) throws SQLException {
        return new GameEntity(
            resultSet.getLong("id"),
            Turn.valueOf(resultSet.getString("turn")),
            GameStatus.valueOf(resultSet.getString("status"))
        );
    }
}
