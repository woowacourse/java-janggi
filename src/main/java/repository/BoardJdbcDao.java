package repository;

import domain.board.Board;
import domain.board.Piece;
import domain.board.Team;
import domain.board.Type;
import domain.vo.Position;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class BoardJdbcDao implements BoardDao {

    @Override
    public void saveBoard(Connection con, Long gameId, Board board) {
        String sql = "insert into boards(game_id, position_col, position_row, team, piece_type, created_at, updated_at) values(?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            OffsetDateTime now = OffsetDateTime.now();

            Map<Position, Piece> boardMapper = board.getBoard();
            for (Position position : boardMapper.keySet()) {
                pstmt.setLong(1, gameId);
                pstmt.setInt(2, position.getCol());
                pstmt.setInt(3, position.getRow());
                pstmt.setString(4, boardMapper.get(position).getTeam().name());
                pstmt.setString(5, boardMapper.get(position).getType().name());
                pstmt.setObject(6, now);
                pstmt.setObject(7, now);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
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
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
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
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Board findByGameId(Connection con, Long gameId) {
        String sql = "select * from boards where game_id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setLong(1, gameId);

            try (ResultSet rs = pstmt.executeQuery()) {
                Map<Position, Piece> boardMapper = new HashMap<>();
                while (rs.next()) {
                    boardMapper.put(
                            Position.of(
                                    rs.getInt("position_row"),
                                    rs.getInt("position_col")
                            ),
                            Piece.of(
                                    Team.valueOf(rs.getString("team")),
                                    Type.valueOf(rs.getString("piece_type")),
                                    Type.valueOf(rs.getString("piece_type")).createStrategy()
                            ));
                }
                return Board.of(boardMapper);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
