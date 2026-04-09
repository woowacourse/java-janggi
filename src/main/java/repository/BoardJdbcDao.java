package repository;

import entity.BoardEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardJdbcDao implements BoardDao {

    @Override
    public void saveAll(Connection con, List<BoardEntity> boards) {
        String sql = "insert into boards(game_id, position_row, position_col, team, piece_type) values(?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (BoardEntity board : boards) {
                pstmt.setLong(1, board.getGameId());
                pstmt.setInt(2, board.getPositionRow());
                pstmt.setInt(3, board.getPositionCol());
                pstmt.setString(4, board.getTeam());
                pstmt.setString(5, board.getPieceType());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public void deleteByPosition(Connection con, Long gameId, int row, int col) {
        String sql = "delete from boards where game_id = ? and position_row = ? and position_col = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setLong(1, gameId);
            pstmt.setInt(2, row);
            pstmt.setInt(3, col);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public void updatePosition(Connection con, Long gameId, int fromRow, int fromCol, int toRow, int toCol) {
        String sql = "update boards set position_row = ?, position_col = ? " +
                "where game_id = ? and position_row = ? and position_col = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

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
    public List<BoardEntity> findAllByGameId(Long gameId) {
        String sql = "select * from boards where game_id = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<BoardEntity> boards = new ArrayList<>();
                while (rs.next()) {
                    BoardEntity board = new BoardEntity(
                            rs.getLong("id"),
                            rs.getLong("game_id"),
                            rs.getInt("position_row"),
                            rs.getInt("position_col"),
                            rs.getString("team"),
                            rs.getString("piece_type")
                    );
                    boards.add(board);
                }
                return boards;
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] " + e.getMessage());
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
