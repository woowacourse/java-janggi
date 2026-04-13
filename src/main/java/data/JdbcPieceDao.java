package data;

import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceDao implements PieceDao {

    @Override
    public void insertAll(Connection conn, Long gameId, List<PieceEntity> pieces) {
        String sql = """
                INSERT INTO piece (game_id, piece_type, side, pos_row, pos_col)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (PieceEntity piece : pieces) {
                ps.setLong(1, gameId);
                ps.setString(2, piece.pieceSymbol().name());
                ps.setString(3, piece.side().name());
                ps.setInt(4, piece.row());
                ps.setInt(5, piece.column());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            throw new IllegalArgumentException("piece 저장 중 오류가 발생했습니다. gameId=" + gameId, e);
        }
    }

    @Override
    public List<PieceEntity> findByGameId(Connection conn, Long gameId) {
        String sql = """
                SELECT id, game_id, piece_type, side, pos_row, pos_col
                FROM piece
                WHERE game_id = ?
                ORDER BY pos_row, pos_col
                """;

        List<PieceEntity> result = new ArrayList<>();

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
        String sql = "DELETE FROM piece WHERE game_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, gameId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("piece 삭제 중 오류가 발생했습니다. gameId=" + gameId, e);
        }
    }

    private PieceEntity toPieceDto(ResultSet rs) throws SQLException {
        return new PieceEntity(
                rs.getLong("id"),
                rs.getLong("game_id"),
                PieceSymbol.valueOf(rs.getString("piece_type")),
                Side.valueOf(rs.getString("side")),
                rs.getInt("pos_row"),
                rs.getInt("pos_col")
        );
    }
}
