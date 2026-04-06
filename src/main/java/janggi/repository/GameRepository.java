package janggi.repository;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameRepository {
    private final Connection conn;

    public GameRepository(Connection conn) {
        this.conn = conn;
    }

    public int createGame(Map<Position, Piece> board) throws SQLException {
        int gameId = insertGame();
        savePieces(gameId, board);
        return gameId;
    }

    public Optional<GameInfo> findGame() throws SQLException {
        String sql = "SELECT * FROM game";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        if (!rs.next()) {
            return Optional.empty();
        }
        return Optional.of(new GameInfo(
                rs.getInt("id"),
                rs.getString("current_turn"),
                rs.getString("winner")));
    }

    public List<PieceInfo> findPieces(int gameId) throws SQLException {
        String sql = "SELECT type, team, piece_row, piece_col FROM piece WHERE game_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, gameId);

        ResultSet rs = pstmt.executeQuery();
        List<PieceInfo> pieces = new ArrayList<>();
        while (rs.next()) {
            pieces.add(new PieceInfo(
                    rs.getString("type"),
                    rs.getString("team"),
                    rs.getInt("piece_row"),
                    rs.getInt("piece_col")));
        }
        return pieces;
    }

    private int insertGame() throws SQLException {
        String sql = "INSERT INTO game (current_turn) VALUES(?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, Team.FIRST_TURN.name());
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }

    private void savePieces(int gameId, Map<Position, Piece> board) throws SQLException {
        String sql = "INSERT INTO piece (game_id, type, team, piece_row, piece_col) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            addPieceBatch(pstmt, gameId, entry.getKey(), entry.getValue());
        }
        pstmt.executeBatch();
    }

    private void addPieceBatch(PreparedStatement pstmt, int gameId, Position position, Piece piece) throws SQLException {
        if (piece.isEmptyPiece()) {
            return;
        }
        pstmt.setInt(1, gameId);
        pstmt.setString(2, piece.getType().name());
        pstmt.setString(3, piece.getTeam().name());
        pstmt.setInt(4, position.getRowValue());
        pstmt.setInt(5, position.getColumnValue());
        pstmt.addBatch();
    }
}
