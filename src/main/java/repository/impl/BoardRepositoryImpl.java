package repository.impl;

import java.sql.Connection;
import domain.place.Place;
import domain.place.piece.Side;
import domain.position.Position;
import domain.board.BoardFactory;
import domain.place.piece.PieceFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import repository.BoardRepository;

public class BoardRepositoryImpl implements BoardRepository {

    private static final String DELETE_SQL =
            "DELETE FROM board_piece WHERE game_room_id = ?";

    private static final String INSERT_SQL =
            "INSERT INTO board_piece (game_room_id, position_row, position_col, side, type) VALUES (?, ?, ?, ?, ?)";

    private static final String SELECT_SQL =
            "SELECT position_row, position_col, side, type FROM board_piece WHERE game_room_id = ?";


    @Override
    public void saveBoard(long roomId, Map<Position, Place> board, Connection conn) {
        try {
            deleteExisting(conn, roomId);
            insertBoard(conn, roomId, board);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 상태 저장 중 오류가 발생했습니다.", e);
        }
    }

    private void deleteExisting(Connection conn, long roomId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            stmt.setLong(1, roomId);
            stmt.executeUpdate();
        }
    }

    private void insertBoard(Connection conn, long roomId, Map<Position, Place> board) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            for (Map.Entry<Position, Place> entry : board.entrySet()) {
                if (entry.getValue().getSide().isPresent()) {
                    addBatch(stmt, roomId, entry);
                }
            }
            stmt.executeBatch();
        }
    }

    private void addBatch(PreparedStatement stmt, long roomId, Map.Entry<Position, Place> entry) throws SQLException {
        Position position = entry.getKey();
        Place place = entry.getValue();

        stmt.setLong(1, roomId);
        stmt.setInt(2, position.getRow());
        stmt.setInt(3, position.getColumn());
        stmt.setString(4, place.getSide().get().getName());
        stmt.setString(5, place.getFormat());

        stmt.addBatch();
    }

    @Override
    public Map<Position, Place> findBoard(long roomId, Connection conn) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_SQL)) {

            stmt.setLong(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                return extractBoard(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 보드 상태 조회 중 오류가 발생했습니다.", e);
        }
    }

    private Map<Position, Place> extractBoard(ResultSet rs) throws SQLException {
        Map<Position, Place> board = BoardFactory.setUpEmpty();

        while (rs.next()) {
            board.put(toPosition(rs), toPlace(rs));
        }

        return board;
    }

    private Position toPosition(ResultSet rs) throws SQLException {
        return new Position(
                rs.getInt("position_row"),
                rs.getInt("position_col")
        );
    }

    private Place toPlace(ResultSet rs) throws SQLException {
        Side side = Side.from(rs.getString("side"));
        String type = rs.getString("type");
        return PieceFactory.from(type).createPlace(side);
    }
}
