package repository;

import entity.PieceEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PieceJdbcDao implements PieceDao {

    @Override
    public void saveAll(List<PieceEntity> pieces) {
        String sql = "insert into pieces(game_id, position_row, position_col, team, piece_type) values(?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            for (PieceEntity piece : pieces) {
                pstmt.setLong(1, piece.getGameId());
                pstmt.setInt(2, piece.getPositionRow());
                pstmt.setInt(3, piece.getPositionCol());
                pstmt.setString(4, piece.getTeam());
                pstmt.setString(5, piece.getPieceType());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public void deleteByPosition(Long gameId, int row, int col) {
        String sql = "delete from pieces where game_id = ? and position_row = ? and position_col = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setLong(1, gameId);
            pstmt.setInt(2, row);
            pstmt.setInt(3, col);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public void updatePosition(Long gameId, int fromRow, int fromCol, int toRow, int toCol) {
        String sql = "update pieces set position_row = ?, position_col = ? " +
                "where game_id = ? and position_row = ? and position_col = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, toRow);
            pstmt.setInt(2, toCol);
            pstmt.setLong(3, gameId);
            pstmt.setInt(4, fromRow);
            pstmt.setInt(5, fromCol);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(Long gameId) {
        String sql = "select * from pieces where game_id = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setLong(1, gameId);

            ResultSet rs = pstmt.executeQuery();

            List<PieceEntity> pieces = new ArrayList<>();
            while (rs.next()) {
                PieceEntity piece = new PieceEntity(
                        rs.getLong("id"),
                        rs.getLong("game_id"),
                        rs.getInt("position_row"),
                        rs.getInt("position_col"),
                        rs.getString("team"),
                        rs.getString("piece_type")
                );
                pieces.add(piece);
            }
            return pieces;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
