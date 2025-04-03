package janggi.repository.mysql;

import janggi.GameId;
import janggi.GameStatus;
import janggi.player.Score;
import janggi.player.Turn;
import janggi.repository.GameRepository;
import janggi.repository.dto.GameDto;
import janggi.repository.util.ResultSetReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class GameMysqlRepository implements GameRepository {

    @Override
    public GameId save(final Connection connection,
                       final Turn turn,
                       final Score choScore,
                       final Score hanScore) {

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

            return extractGeneratedId(ps);

        } catch (final SQLException e) {
            throw new RuntimeException("게임 저장 중 오류 발생", e);
        }
    }

    @Override
    public GameId save(final Connection connection,
                       final GameId id,
                       final Turn turn,
                       final Score choScore,
                       final Score hanScore) {

        final String sql = """
                INSERT INTO game (id, status, turn, cho_score, han_score)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    status = VALUES(status),
                    turn = VALUES(turn),
                    cho_score = VALUES(cho_score),
                    han_score = VALUES(han_score);
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id.getValue());
            ps.setString(2, GameStatus.RUNNING.name());
            ps.setInt(3, turn.getAccumulatedCount());
            ps.setInt(4, choScore.value());
            ps.setInt(5, hanScore.value());
            ps.executeUpdate();

            return id;
        } catch (final SQLException e) {
            throw new RuntimeException("게임 저장 중 오류 발생", e);
        }
    }

    @Override
    public Optional<GameDto> findById(final Connection connection,
                                      final GameId id) {
        final String sql = """
                SELECT id, status, turn, cho_score, han_score, started_at, last_saved_at
                FROM game
                WHERE id = ?
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id.getValue());

            try (final ResultSet resultSet = ps.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(new GameDto(
                        resultSet.getInt("id"),
                        resultSet.getString("status"),
                        resultSet.getInt("turn"),
                        resultSet.getInt("cho_score"),
                        resultSet.getInt("han_score"),
                        resultSet.getTimestamp("started_at").toLocalDateTime(),
                        resultSet.getTimestamp("last_saved_at").toLocalDateTime()
                ));
            }
        } catch (final SQLException e) {
            throw new RuntimeException("게임 조회 중 오류 발생", e);
        }
    }

    @Override
    public List<GameDto> findAllRunning(final Connection connection) {
        final String sql = """
                SELECT id, status, turn, cho_score, han_score, started_at, last_saved_at
                FROM game
                WHERE status = ?
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, GameStatus.RUNNING.name());

            try (final ResultSet resultSet = ps.executeQuery()) {
                return ResultSetReader.toList(resultSet,
                        result -> new GameDto(
                                resultSet.getInt("id"),
                                resultSet.getString("status"),
                                resultSet.getInt("turn"),
                                resultSet.getInt("cho_score"),
                                resultSet.getInt("han_score"),
                                resultSet.getTimestamp("started_at").toLocalDateTime(),
                                resultSet.getTimestamp("last_saved_at").toLocalDateTime()));
            }
        } catch (final SQLException e) {
            throw new RuntimeException("게임 조회 중 오류 발생", e);
        }
    }

    @Override
    public void updateStatusById(final Connection connection, final GameId id, final GameStatus status) {
        final String sql = """
                    UPDATE game
                    SET status = ?
                    WHERE id = ?
                """;

        try (final PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, GameStatus.FINISHED.name());
            ps.setLong(2, id.getValue());
            ps.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException("게임 상태 업데이트 중 오류 발생", e);
        }
    }

    private GameId extractGeneratedId(final PreparedStatement ps) throws SQLException {
        try (final ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return GameId.from(rs.getLong(1));
            }
            throw new SQLException("게임 ID 생성 실패");
        }
    }
}
