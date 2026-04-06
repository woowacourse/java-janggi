package repository;

import entity.PieceEntity;

import java.sql.*;
import java.util.List;

public class PieceJdbcDao implements PieceDao {

    @Override
    public void saveAll(List<PieceEntity> pieces) {
        String sql = "insert into pieces(game_id, position_row, position_col, team, piece_type) values(?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
        } finally {
            close(con, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

    private void close(Connection con, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] " + e.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] " + e.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException("[ERROR] " + e.getMessage());
            }
        }
    }
}
