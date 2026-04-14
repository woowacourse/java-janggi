package data;

import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcBoardDao implements BoardDao {

    @Override
    public void insertAll(Connection conn, Long gameId, List<BoardEntity> pieces) {
        String sql = """
                INSERT INTO board (game_id, piece_id, pos_row, pos_col)
                SELECT ?, id, ?, ?
                FROM piece
                WHERE piece_type = ? AND side = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (BoardEntity piece : pieces) {
                ps.setLong(1, gameId);
                ps.setInt(2, piece.row());
                ps.setInt(3, piece.column());
                ps.setString(4, piece.pieceSymbol().name());
                ps.setString(5, piece.side().name());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            throw new IllegalArgumentException("board 저장 중 오류가 발생했습니다. gameId=" + gameId, e);
        }
    }

    @Override
    public List<BoardEntity> findByGameId(Connection conn, Long gameId) {
        String sql = """
                SELECT b.id, b.game_id, p.piece_type, p.side, b.pos_row, b.pos_col
                FROM board b
                JOIN piece p ON b.piece_id = p.id
                WHERE b.game_id = ?
                ORDER BY b.pos_row, b.pos_col
                """;

        List<BoardEntity> result = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, gameId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(toPieceDto(rs));
                }
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalArgumentException("piece을 찾는 도중 오류가 발생했습니다. gameId=" + gameId, e);
        }
    }

    @Override
    public void deleteByGameId(Connection conn, Long gameId) {
        String sql = "DELETE FROM board WHERE game_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("board 삭제 중 오류가 발생했습니다. gameId=" + gameId, e);
        }
    }

    private BoardEntity toPieceDto(ResultSet rs) throws SQLException {
        return new BoardEntity(
                rs.getLong("id"),
                rs.getLong("game_id"),
                PieceSymbol.valueOf(rs.getString("piece_type")),
                Side.valueOf(rs.getString("side")),
                rs.getInt("pos_row"),
                rs.getInt("pos_col")
        );
    }
}
