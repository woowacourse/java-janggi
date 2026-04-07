package janggi.repository;

import janggi.domain.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import janggi.infrastructure.DBConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceRepository {

    private static final String INSERT_PIECE = "INSERT INTO piece (game_id, row_pos, col_pos, piece_type, team) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_GAME_ID = "SELECT row_pos, col_pos, piece_type, team FROM piece WHERE game_id = ?";
    private static final String DELETE_BY_POSITION = "DELETE FROM piece WHERE game_id = ? AND row_pos = ? AND col_pos = ?";
    private static final String UPDATE_POSITION = "UPDATE piece SET row_pos = ?, col_pos = ? WHERE game_id = ? AND row_pos = ? AND col_pos = ?";

    public void saveAll(Long gameId, Board board) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_PIECE)) {

            Map<Position, Piece> pieces = board.findAllPieces();
            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                pstmt.setLong(1, gameId);
                pstmt.setInt(2, position.getRow());
                pstmt.setInt(3, position.getCol());
                pstmt.setString(4, PieceType.from(piece).name());
                pstmt.setString(5, piece.findTeam().name());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장에 실패했습니다.", e);
        }
    }

    public Board findByGameId(Long gameId) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_GAME_ID)) {

            pstmt.setLong(1, gameId);
            ResultSet rs = pstmt.executeQuery();

            Map<Position, Piece> pieces = new HashMap<>();
            while (rs.next()) {
                Position position = new Position(rs.getInt("row_pos"), rs.getInt("col_pos"));
                PieceType pieceType = PieceType.valueOf(rs.getString("piece_type"));
                Team team = Team.valueOf(rs.getString("team"));
                Piece piece = pieceType.createPiece(team);
                pieces.put(position, piece);
            }
            return Board.of(pieces);

        } catch (SQLException e) {
            throw new RuntimeException("기물 조회에 실패했습니다.", e);
        }
    }

    public void movePiece(Long gameId, Position from, Position to) {
        deleteByPosition(gameId, to);
        updatePosition(gameId, from, to);
    }

    private void deleteByPosition(Long gameId, Position position) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_BY_POSITION)) {

            pstmt.setLong(1, gameId);
            pstmt.setInt(2, position.getRow());
            pstmt.setInt(3, position.getCol());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제에 실패했습니다.", e);
        }
    }

    private void updatePosition(Long gameId, Position from, Position to) {
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_POSITION)) {

            pstmt.setInt(1, to.getRow());
            pstmt.setInt(2, to.getCol());
            pstmt.setLong(3, gameId);
            pstmt.setInt(4, from.getRow());
            pstmt.setInt(5, from.getCol());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("기물 이동 갱신에 실패했습니다.", e);
        }
    }
}
