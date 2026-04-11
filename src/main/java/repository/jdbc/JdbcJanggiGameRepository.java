package repository.jdbc;

import domain.piece.Side;
import janggigame.GameMetaData;
import janggigame.JanggiGameStatus;
import repository.JanggiGameRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public class JdbcJanggiGameRepository implements JanggiGameRepository {
    private final DataSource dataSource;

    public JdbcJanggiGameRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public GameMetaData save(GameMetaData gameMetaData) {
        String sql = """
                INSERT INTO game (current_turn, status, cho_janggun_count, han_janggun_count, created_at)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, gameMetaData.currentTurn().name());
            statement.setString(2, gameMetaData.status().name());
            statement.setInt(3, 0);
            statement.setInt(4, 0);
            statement.setTimestamp(5, Timestamp.from(Instant.now()));
            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new IllegalStateException("새 게임 저장에 실패했습니다.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new IllegalStateException("생성된 게임 ID를 반환받지 못했습니다.");
                }
                return new GameMetaData(
                        generatedKeys.getLong("id"),
                        gameMetaData.status(),
                        gameMetaData.currentTurn(),
                        gameMetaData.choJangGunCount(),
                        gameMetaData.hanJangGunCount()
                );
            }
        } catch (SQLException e) {
            throw new IllegalStateException("새 게임 저장에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<GameMetaData> findLatestUnfinishedGame() {
        String sql = """
                SELECT id, current_turn, status, cho_janggun_count, han_janggun_count, created_at
                FROM game
                WHERE status <> ?
                ORDER BY created_at DESC
                LIMIT 1
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, JanggiGameStatus.FINISHED.name());

            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }

                GameMetaData gameMetaData = new GameMetaData(
                        rs.getLong("id"),
                        JanggiGameStatus.valueOf(rs.getString("status")),
                        Side.valueOf(rs.getString("current_turn")),
                        rs.getInt("cho_janggun_count"),
                        rs.getInt("han_janggun_count")
                );
                return Optional.of(gameMetaData);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("최근 미종료 게임 조회에 실패했습니다.", e);
        }
    }

    @Override
    public void updateGameStatusById(Long gameId, JanggiGameStatus newStatus) {
        try (Connection connection = dataSource.getConnection()) {
            updateGameStatusById(gameId, newStatus, connection);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 수정에 실패했습니다.", e);
        }
    }

    @Override
    public void updateGameStatusById(Long gameId, JanggiGameStatus newStatus, Connection connection) {
        String sql = " UPDATE game SET status = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newStatus.name());
            statement.setLong(2, gameId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new IllegalStateException("수정된 게임이 없습니다.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 수정에 실패했습니다.", e);
        }
    }

    @Override
    public void updateJangGunCountById(Map<Side, Integer> jangGunCount, Long gameId) {
        try (Connection connection = dataSource.getConnection()) {
            updateJangGunCountById(jangGunCount, gameId, connection);
        } catch (SQLException e) {
            throw new IllegalStateException("진영 별 장군 횟수를 업데이트하지 못했습니다.", e);
        }
    }

    @Override
    public void updateJangGunCountById(Map<Side, Integer> jangGunCount, Long gameId, Connection connection) {
        String sql = "UPDATE game SET cho_janggun_count = ?, han_janggun_count = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, jangGunCount.getOrDefault(Side.CHO, 0));
            statement.setInt(2, jangGunCount.getOrDefault(Side.HAN, 0));
            statement.setLong(3, gameId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new IllegalStateException("장군 횟수를 수정할 게임이 정확히 1개여야 합니다.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("진영 별 장군 횟수를 업데이트하지 못했습니다.", e);
        }
    }

    @Override
    public void updateTurnById(Side currentTurnSide, Long gameId) {
        try (Connection connection = dataSource.getConnection()) {
            updateTurnById(currentTurnSide, gameId, connection);
        } catch (SQLException e) {
            throw new IllegalStateException("현재 차례의 진영을 수정하지 못하였습니다.", e);
        }
    }

    @Override
    public void updateTurnById(Side currentTurnSide, Long gameId, Connection connection) {
        String sql = "UPDATE game SET current_turn = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, currentTurnSide.name());
            statement.setLong(2, gameId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows != 1) {
                throw new IllegalStateException("현재 차례를 수정할 게임이 정확히 1개여야 합니다.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("현재 차례의 진영을 수정하지 못하였습니다.", e);
        }
    }
}
