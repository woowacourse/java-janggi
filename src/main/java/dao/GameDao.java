package dao;

import config.ConnectionFactory;
import dto.dao.InitialGamePersistDto;
import dto.dao.MovePersistDto;
import dto.dao.PiecePlacement;
import entity.GameEntity;
import entity.PieceEntity;
import entity.ResumableGameEntity;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDao {
    private final ConnectionFactory connectionFactory;

    public GameDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private static long extract(ResultSet keys) throws SQLException {
        validateUpdateResult(!keys.next(), "game id 생성 실패");
        return keys.getLong(1);
    }

    private static void setupBatchForPieces(long gameId, List<PiecePlacement> placements, PreparedStatement ps)
            throws SQLException {
        for (PiecePlacement p : placements) {
            ps.setLong(1, gameId);
            ps.setString(2, p.team());
            ps.setString(3, p.pieceType());
            ps.setInt(4, p.y());
            ps.setInt(5, p.x());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    private static void addResumableGames(ResultSet rs, List<ResumableGameEntity> out) throws SQLException {
        while (rs.next()) {
            out.add(ResumableGameEntity.fromRow(rs));
        }
    }

    private static List<PieceEntity> getLoadedPieces(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            return collectPieces(rs);
        }
    }

    private static List<PieceEntity> collectPieces(ResultSet rs) throws SQLException {
        List<PieceEntity> pieces = new ArrayList<>();
        while (rs.next()) {
            pieces.add(PieceEntity.fromRow(rs));
        }
        return pieces;
    }

    private static GameEntity getLoadedGameState(long gameId, List<PieceEntity> pieces, PreparedStatement ps)
            throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            return GameEntity.fromHeaderResultSet(gameId, rs, pieces);
        }
    }

    private static Optional<Long> getPieceId(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            return getPieceId(rs);
        }
    }

    private static Optional<Long> getPieceId(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return Optional.of(rs.getLong(1));
        }
        return Optional.empty();
    }

    private static void executePieceDeletion(long pieceId, PreparedStatement ps) throws SQLException {
        validateUpdateResult(ps.executeUpdate() != 1, "piece 삭제 실패: id=" + pieceId);
    }

    private static void executePieceUpdate(long pieceId, PreparedStatement ps) throws SQLException {
        validateUpdateResult(ps.executeUpdate() != 1, "piece 위치 갱신 실패: id=" + pieceId);
    }

    private static void validateUpdateResult(boolean isFailure, String errorMessage) {
        if (isFailure) {
            throw new IllegalStateException(errorMessage);
        }
    }

    public long insertInitialGameAndPieces(Connection conn, InitialGamePersistDto dto) throws SQLException {
        long gameId = insertGameRow(conn, dto.inProgress(), dto.turnTeam(), dto.choScore(), dto.hanScore());
        savePieces(conn, gameId, dto.placements());
        return gameId;
    }

    private long insertGameRow(
            Connection conn,
            boolean inProgress,
            String turnTeam,
            double choScore,
            double hanScore
    ) throws SQLException {
        String sql = """
                INSERT INTO game (status, turn_team, cho_score, han_score, winner_team)
                VALUES (?, ?, ?, ?, NULL)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, inProgress);
            ps.setString(2, turnTeam);
            ps.setBigDecimal(3, BigDecimal.valueOf(choScore));
            ps.setBigDecimal(4, BigDecimal.valueOf(hanScore));
            ps.executeUpdate();
            return extractGeneratedId(ps);
        }
    }

    private long extractGeneratedId(PreparedStatement ps) throws SQLException {
        try (ResultSet keys = ps.getGeneratedKeys()) {
            return extract(keys);
        }
    }

    private void savePieces(Connection conn, long gameId, List<PiecePlacement> placements) throws SQLException {
        String sql = """
                INSERT INTO piece (game_id, team, piece_type, y, x)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            setupBatchForPieces(gameId, placements, ps);
        }
    }

    public List<ResumableGameEntity> findResumableGames() {
        String sql = """
                SELECT id, cho_score, han_score, updated_at
                  FROM game
                 WHERE winner_team IS NULL
                 ORDER BY updated_at DESC, id DESC
                """;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<ResumableGameEntity> out = new ArrayList<>();
            addResumableGames(rs, out);
            return out;
        } catch (SQLException e) {
            throw new IllegalStateException("재개 가능 게임 조회 실패", e);
        }
    }

    public GameEntity loadGameForResume(long gameId) {
        try (Connection conn = connectionFactory.getConnection()) {
            List<PieceEntity> pieces = queryPieceRows(conn, gameId);
            return queryLoadedGameState(conn, gameId, pieces);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 로드 실패", e);
        }
    }

    private List<PieceEntity> queryPieceRows(Connection conn, long gameId) throws SQLException {
        String sql = """
                SELECT team, piece_type, y, x
                  FROM piece
                 WHERE game_id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            return getLoadedPieces(ps);
        }
    }

    private GameEntity queryLoadedGameState(Connection conn, long gameId, List<PieceEntity> pieces)
            throws SQLException {
        String sql = """
                SELECT cho_score, han_score, turn_team
                  FROM game
                 WHERE id = ? AND winner_team IS NULL
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            return getLoadedGameState(gameId, pieces, ps);
        }
    }

    public Optional<Long> findPieceIdAt(long gameId, int y, int x) {
        String sql = "SELECT id FROM piece WHERE game_id = ? AND y = ? AND x = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            ps.setInt(2, y);
            ps.setInt(3, x);
            return getPieceId(ps);
        } catch (SQLException e) {
            throw new IllegalStateException("기물 조회 실패", e);
        }
    }

    public void persistMove(Connection conn, MovePersistDto dto) throws SQLException {
        if (dto.capturedPieceIdOrNull() != null) {
            deletePieceById(conn, dto.capturedPieceIdOrNull());
        }
        updatePiecePosition(conn, dto.movedPieceId(), dto.toX(), dto.toY());
        updateGameAfterMove(
                conn,
                dto.gameId(),
                dto.inProgress(),
                dto.turnTeam(),
                dto.choScore(),
                dto.hanScore(),
                dto.winnerTeamOrNull()
        );
    }

    private void deletePieceById(Connection conn, long pieceId) throws SQLException {
        String sql = "DELETE FROM piece WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, pieceId);
            executePieceDeletion(pieceId, ps);
        }
    }

    private void updatePiecePosition(Connection conn, long pieceId, int x, int y) throws SQLException {
        String sql = """
                UPDATE piece
                   SET x = ?, y = ?, updated_at = ?
                 WHERE id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, x);
            ps.setInt(2, y);
            ps.setTimestamp(3, Timestamp.from(Instant.now()));
            ps.setLong(4, pieceId);
            executePieceUpdate(pieceId, ps);
        }
    }

    private void updateGameAfterMove(
            Connection conn,
            long gameId,
            boolean inProgress,
            String turnTeam,
            double choScore,
            double hanScore,
            String winnerTeamOrNull
    ) throws SQLException {
        String sql = """
                UPDATE game
                   SET status = ?,
                       turn_team = ?,
                       cho_score = ?,
                       han_score = ?,
                       winner_team = ?,
                       updated_at = ?
                 WHERE id = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, inProgress);
            ps.setString(2, turnTeam);
            ps.setBigDecimal(3, BigDecimal.valueOf(choScore));
            ps.setBigDecimal(4, BigDecimal.valueOf(hanScore));
            ps.setString(5, winnerTeamOrNull);
            ps.setTimestamp(6, Timestamp.from(Instant.now()));
            ps.setLong(7, gameId);
            validateUpdateResult(ps.executeUpdate() != 1, "game 갱신 실패: id=" + gameId);
        }
    }
}
