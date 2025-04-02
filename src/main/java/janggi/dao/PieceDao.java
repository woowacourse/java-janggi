package janggi.dao;

import janggi.exception.DataAccessException;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceDao {

    private final Connection connection;

    public PieceDao(Connection connection) {
        this.connection = connection;
    }

    public void saveAll(Long boardId, Map<Position, Piece> pieces) {
        String sql = "INSERT INTO piece (board_id, type, camp, x, y) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                PieceVO vo = PieceVO.of(boardId, entry.getKey(), entry.getValue());
                statement.setLong(1, vo.boardId());
                statement.setString(2, vo.type());
                statement.setString(3, vo.camp());
                statement.setInt(4, vo.x());
                statement.setInt(5, vo.y());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new DataAccessException("Piece를 저장하는데 실패했습니다.", e);
        }
    }

    public Map<Position, Piece> findAllByBoardId(Long boardId) {
        String sql = "SELECT * FROM piece WHERE board_id = ?";
        Map<Position, Piece> result = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PieceVO vo = new PieceVO(
                            resultSet.getLong("id"),
                            resultSet.getLong("board_id"),
                            resultSet.getString("type"),
                            resultSet.getString("camp"),
                            resultSet.getInt("x"),
                            resultSet.getInt("y")
                    );
                    result.put(vo.toPosition(), vo.toPiece());
                }
            }
            return result;
        } catch (SQLException e) {
            throw new DataAccessException("Piece 데이터를 불러오는 데 실패했습니다.", e);
        }
    }
}
