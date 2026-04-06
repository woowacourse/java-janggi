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

    public int createGame(Map<Position, Piece> board) {
        try {
            int gameId = insertGame();
            savePieces(gameId, board);
            return gameId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<GameInfo> findGame() {
        try {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PieceInfo> findPieces(int gameId) {
        try {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void movePiece(int gameId, Position from, Position to) {
        try {
            String deleteSql = "DELETE FROM piece WHERE game_id = ? AND piece_row = ? AND piece_col = ?";
            PreparedStatement deletePstmt = conn.prepareStatement(deleteSql);
            deletePstmt.setInt(1, gameId);
            deletePstmt.setInt(2, to.getRowValue());
            deletePstmt.setInt(3, to.getColumnValue());
            deletePstmt.executeUpdate();

            String updateSql = "UPDATE piece SET piece_row = ?, piece_col = ? WHERE game_id = ? AND piece_row = ? AND piece_col = ?";
            PreparedStatement updatePstmt = conn.prepareStatement(updateSql);
            updatePstmt.setInt(1, to.getRowValue());
            updatePstmt.setInt(2, to.getColumnValue());
            updatePstmt.setInt(3, gameId);
            updatePstmt.setInt(4, from.getRowValue());
            updatePstmt.setInt(5, from.getColumnValue());
            updatePstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTurn(int gameId, Team nextTurn) {
        try {
            String sql = "UPDATE game SET current_turn = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nextTurn.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateWinner(int gameId, Team winner) {
        try {
            String sql = "UPDATE game SET winner = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, winner.name());
            pstmt.setInt(2, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteGame(int gameId) {
        try {
            String sql = "DELETE FROM game WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
