package janggi.dao;

import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private final DataSource dataSource;

    public PieceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveAll(List<PieceEntity> pieces) {
        String sql = "INSERT INTO piece (game_id, x, y, team, piece_type) VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            for (PieceEntity piece : pieces) {
                pstmt.setLong(1, piece.gameId());
                pstmt.setInt(2, piece.x());
                pstmt.setInt(3, piece.y());
                pstmt.setString(4, piece.team().name());
                pstmt.setString(5, piece.pieceType().name());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 저장 중 오류가 발생했습니다.", e);
        }
    }

    public List<PieceEntity> findByGameId(long gameId) {
        String sql = "SELECT game_id, x, y, team, piece_type FROM piece WHERE game_id = ?";

        List<PieceEntity> pieces = new ArrayList<>();

        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    pieces.add(new PieceEntity(
                        rs.getLong("game_id"),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        Team.valueOf(rs.getString("team")),
                        PieceType.valueOf(rs.getString("piece_type"))
                    ));
                }
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 조회 중 오류가 발생했습니다.", e);
        }
    }

    public void move(Connection conn, long gameId, Position from, Position to) {
        String deleteTargetSql = "DELETE FROM piece WHERE game_id = ? AND x = ? AND y = ?";
        String updateSourceSql = "UPDATE piece SET x = ?, y = ? WHERE game_id = ? AND x = ? AND y = ?";

        try (
            PreparedStatement deletePstmt = conn.prepareStatement(deleteTargetSql);
            PreparedStatement updatePstmt = conn.prepareStatement(updateSourceSql)
        ) {
            deletePstmt.setLong(1, gameId);
            deletePstmt.setInt(2, to.x());
            deletePstmt.setInt(3, to.y());
            deletePstmt.executeUpdate();

            updatePstmt.setInt(1, to.x());
            updatePstmt.setInt(2, to.y());
            updatePstmt.setLong(3, gameId);
            updatePstmt.setInt(4, from.x());
            updatePstmt.setInt(5, from.y());
            updatePstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물 이동 중 오류가 발생했습니다.", e);
        }
    }
}
