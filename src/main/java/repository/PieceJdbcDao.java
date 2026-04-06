package repository;

import entity.PieceEntity;

import java.sql.*;
import java.util.ArrayList;
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

    @Override
    public void deleteByPosition(Long gameId, int row, int col) {
        String sql = "delete from pieces where game_id = ? and position_row = ? and position_col = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            pstmt.setLong(1, gameId);
            pstmt.setInt(2, row);
            pstmt.setInt(3, col);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public void updatePosition(Long gameId, int fromRow, int fromCol, int toRow, int toCol) {
        String sql = "update pieces set position_row = ?, position_col = ? " +
                "where game_id = ? and position_row = ? and position_col = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, toRow);
            pstmt.setInt(2, toCol);
            pstmt.setLong(3, gameId);
            pstmt.setInt(4, fromRow);
            pstmt.setInt(5, fromCol);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        } finally {
            close(con, pstmt, rs);
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(Long gameId) {
        String sql = "select * from pieces where game_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, gameId);

            rs = pstmt.executeQuery();

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
