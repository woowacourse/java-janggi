package janggi.infrastructure;

import janggi.domain.GameRepository;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {

    @Override
    public long save(Board board, Team currentTeam) {
        try (Connection conn = DBConnector.getConnection()) {
            conn.setAutoCommit(false);
            long gameId = saveGame(conn, currentTeam);
            savePieces(conn, gameId, board.showBoard());
            conn.commit();
            return gameId;
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 저장 실패", e);
        }
    }

    @Override
    public void update(long gameId, Board board, Team currentTeam) {
        try (Connection conn = DBConnector.getConnection()) {
            conn.setAutoCommit(false);
            updateTurn(conn, gameId, currentTeam);
            deletePieces(conn, gameId);
            savePieces(conn, gameId, board.showBoard());
            conn.commit();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 업데이트 실패", e);
        }
    }

    @Override
    public Optional<Board> findBoardById(long gameId) {
        if (!existsGame(gameId)) {
            return Optional.empty();
        }
        String sql = "SELECT row_pos, col_pos, team, type FROM piece WHERE janggi_game_id = ?";
        try (Connection conn = DBConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            return Optional.of(Board.from(mapToPieces(rs)));
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 보드 조회 실패", e);
        }
    }

    private boolean existsGame(long gameId) {
        String sql = "SELECT id FROM janggi_game WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 게임 존재 여부 확인 실패", e);
        }
    }

    @Override
    public Team findTurnById(long gameId) {
        String sql = "SELECT turn FROM janggi_game WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                throw new IllegalArgumentException("[ERROR] 게임을 찾을 수 없습니다: " + gameId);
            }
            return Team.valueOf(rs.getString("turn"));
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR] 턴 조회 실패", e);
        }
    }

    private long saveGame(Connection conn, Team currentTeam) throws SQLException {
        String sql = "INSERT INTO janggi_game (turn) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, currentTeam.name());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            keys.next();
            return keys.getLong(1);
        }
    }

    private void savePieces(Connection conn, long gameId, Map<Position, Piece> pieces) throws SQLException {
        String sql = "INSERT INTO piece (janggi_game_id, row_pos, col_pos, team, type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                pstmt.setLong(1, gameId);
                pstmt.setInt(2, entry.getKey().getRowValue());
                pstmt.setInt(3, entry.getKey().getColumnValue());
                pstmt.setString(4, PieceFactory.toTeamString(entry.getValue()));
                pstmt.setString(5, entry.getValue().getType().name());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private void updateTurn(Connection conn, long gameId, Team currentTeam) throws SQLException {
        String sql = "UPDATE janggi_game SET turn = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, currentTeam.name());
            pstmt.setLong(2, gameId);
            pstmt.executeUpdate();
        }
    }

    private void deletePieces(Connection conn, long gameId) throws SQLException {
        String sql = "DELETE FROM piece WHERE janggi_game_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        }
    }

    private Map<Position, Piece> mapToPieces(ResultSet rs) throws SQLException {
        Map<Position, Piece> pieces = new LinkedHashMap<>();
        while (rs.next()) {
            Position position = Position.of(rs.getInt("row_pos"), rs.getInt("col_pos"));
            Piece piece = PieceFactory.create(rs.getString("team"), rs.getString("type"));
            pieces.put(position, piece);
        }
        return pieces;
    }
}
