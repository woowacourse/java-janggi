package janggi.repository.mysql;

import janggi.GameStatus;
import janggi.player.Score;
import janggi.player.Turn;
import janggi.repository.GameRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameMysqlRepository implements GameRepository {

    private final Connection connection;

    public GameMysqlRepository(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public Long save(final Turn turn, final Score choScore, final Score hanScore) {
        final String sql = """
                INSERT INTO game (status, turn, cho_score, han_score) 
                VALUES (?, ?, ?, ?)
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, GameStatus.RUNNING.name());
            ps.setInt(2, turn.getAccumulatedCount());
            ps.setInt(3, choScore.value());
            ps.setInt(4, hanScore.value());
            ps.executeUpdate();

            try (final ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1); // 생성된 gameId 반환
                } else {
                    throw new SQLException("게임 ID 생성 실패");
                }
            }

        } catch (final SQLException e) {
            throw new RuntimeException("게임 저장 중 오류 발생", e);
        }
    }

    @Override
    public Long save(final long gameId, final Turn turn, final Score choScore, final Score hanScore) {
        final String sql = """
                INSERT INTO game (id, status, turn, cho_score, han_score)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE\s
                    status = VALUES(status),
                    turn = VALUES(turn),
                    cho_score = VALUES(cho_score),
                    han_score = VALUES(han_score);
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            ps.setString(2, GameStatus.RUNNING.name());
            ps.setInt(3, turn.getAccumulatedCount());
            ps.setInt(4, choScore.value());
            ps.setInt(5, hanScore.value());
            ps.executeUpdate();
            return gameId;
        } catch (final SQLException e) {
            throw new RuntimeException("게임 저장 중 오류 발생", e);
        }
    }

    @Override
    public List<Integer> findIdsByStatus(final GameStatus status) {
        final String sql = """
                SELECT id
                FROM game
                WHERE status = ?
                """;
        final List<Integer> gameIds = new ArrayList<>();

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status.name());
            try (final ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    gameIds.add(rs.getInt("id"));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("게임 ID 조회 중 오류 발생", e);
        }

        return gameIds;
    }

    @Override
    public Optional<Turn> findTurnByGameId(final Long gameId) {
        final String sql = """
                SELECT turn
                FROM game
                WHERE id = ?
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    final int turnValue = rs.getInt("turn");
                    return Optional.of(new Turn(turnValue));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("게임 턴 조회 중 오류 발생", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Score> findChoScoreByGameId(final long gameId) {
        final String sql = """
                SELECT cho_score
                FROM game
                WHERE id = ?
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    final int choScore = rs.getInt("cho_score");
                    return Optional.of(new Score(choScore));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("점수 조회 중 오류 발생", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Score> findHanScoreByGameId(final long gameId) {
        final String sql = """
                SELECT han_score
                FROM game
                WHERE id = ?
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    final int hanScore = rs.getInt("han_score");
                    return Optional.of(new Score(hanScore));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException("점수 조회 중 오류 발생", e);
        }

        return Optional.empty();
    }
}
