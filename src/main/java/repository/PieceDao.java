package repository;

import domain.board.BoardFactory;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

import java.sql.*;
import java.util.Map;

public class PieceDao {

    public void saveAll(long gameId, Map<Position, Piece> pieces) {
        // 1. 기존 기물 데이터 싹 비우기 (DELETE)
        deleteByGameId(gameId);

        // 2. 현재 장기판의 모든 기물을 새로 저장 (INSERT)
        String sql = "INSERT INTO piece (game_id, team, piece_type, x, y) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position pos = entry.getKey();
                Piece piece = entry.getValue();

                pstmt.setLong(1, gameId);
                pstmt.setString(2, piece.getTeam().name());
                pstmt.setString(3, piece.getPieceType().name());
                pstmt.setInt(4, pos.x());
                pstmt.setInt(5, pos.y());
                pstmt.addBatch(); // 여러 건을 한 번에 보내기 위해 배치 사용
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 🌟 추가: DB 데이터를 읽어서 완벽한 Map<Position, Piece>로 조립해 반환합니다.
    public Map<Position, Piece> findByGameId(long gameId) {
        String sql = "SELECT team, piece_type, x, y FROM piece WHERE game_id = ?";
        Map<Position, Piece> pieces = new java.util.HashMap<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // 1. DB에서 글자 꺼내기
                    Team team = Team.valueOf(rs.getString("team"));
                    PieceType type = PieceType.valueOf(rs.getString("piece_type"));
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");

                    // 2. 글자를 진짜 객체로 조립하기! (BoardFactory 활용)
                    Position position = new Position(x, y);
                    Piece piece = BoardFactory.createPiece(type, team);

                    pieces.put(position, piece);
                }
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("기물 불러오기 실패", e);
        }
    }

    private void deleteByGameId(long gameId) {
        String sql = "DELETE FROM piece WHERE game_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, gameId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
