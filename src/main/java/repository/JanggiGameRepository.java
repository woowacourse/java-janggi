package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import domain.Board;
import domain.JanggiGame;
import domain.Position;
import domain.pieces.Piece;

public class JanggiGameRepository {
    private final Connection conn;

    public JanggiGameRepository(Connection conn) {
        this.conn = conn;
    }

    public int saveGame(JanggiGame janggiGame) {
        String sql = "Insert into game (state) VALUES (?);";
        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, janggiGame.getStateValue());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("게임 ID 생성 실패");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveBoard(Board board, int gameId) {
        String sql = """
                Insert into piece_position (game_id, piece_id, x, y, is_killed)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {

                Position position = entry.getKey();
                Piece piece = entry.getValue();

                int pieceId = findByPieceId(piece);

                ps.setInt(1, gameId);
                ps.setInt(2, pieceId);
                ps.setInt(3, position.getX());
                ps.setInt(4, position.getY());
                ps.setBoolean(5, false);
                ps.addBatch(); // ⭐ batch 처리
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findByPieceId(Piece piece) {
        String sql = "Select id from piece where piece_type = ? AND Country = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, piece.getPieceType().getName());
            ps.setString(2, piece.getCountry().name());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("piece_id 없음");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
