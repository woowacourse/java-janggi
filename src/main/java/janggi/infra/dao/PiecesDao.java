package janggi.infra.dao;

import janggi.infra.dto.PieceData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PiecesDao {

    public void save(Long roomId, List<PieceData> data, Connection connection) {
        String sql = "INSERT INTO piece (game_room_id, piece_name, team, row_pos, col_pos) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pieceStatement = connection.prepareStatement(sql)) {
            for (PieceData piece : data) {
                pieceStatement.setLong(1, roomId);
                pieceStatement.setString(2, piece.pieceName());
                pieceStatement.setString(3, piece.teamName());
                pieceStatement.setInt(4, piece.row());
                pieceStatement.setInt(5, piece.column());
                pieceStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물을 저장하던 중 오류가 발생했습니다.", e);
        }
    }

    public void delete(Long roomId, int row, int col, Connection connection) {
        String sql = "DELETE FROM piece WHERE game_room_id = ? AND row_pos = ? AND col_pos = ?";
        try (PreparedStatement killStatement = connection.prepareStatement(sql)) {
            killStatement.setLong(1, roomId);
            killStatement.setInt(2, row);
            killStatement.setInt(3, col);
            killStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 잡힌 기물을 제거하던 중 오류가 발생했습니다.", e);
        }
    }

    public void update(Long roomId, int fromRow, int fromCol, int toRow, int toCol, Connection connection) {
        String movePieceSql = "UPDATE piece SET row_pos = ?, col_pos = ? WHERE game_room_id = ? AND row_pos = ? AND col_pos = ?";
        try (PreparedStatement moveStatement = connection.prepareStatement(movePieceSql)) {
            moveStatement.setInt(1, toRow);
            moveStatement.setInt(2, toCol);
            moveStatement.setLong(3, roomId);
            moveStatement.setInt(4, fromRow);
            moveStatement.setInt(5, fromCol);
            moveStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 기물을 이동하던 종 오류가 발생했습니다.", e);
        }
    }

    public List<PieceData> findAllByRoomId(Long roomId, Connection connection) {
        String sql = "SELECT piece_name, team, row_pos, col_pos FROM piece WHERE game_room_id = ?";
        try (PreparedStatement pieceStatement = connection.prepareStatement(sql)) {
            List<PieceData> piecesData = new ArrayList<>();
            pieceStatement.setLong(1, roomId);
            ResultSet resultSet = pieceStatement.executeQuery();
            while (resultSet.next()) {
                piecesData.add(new PieceData(
                        resultSet.getString("piece_name"),
                        resultSet.getString("team"),
                        resultSet.getInt("row_pos"),
                        resultSet.getInt("col_pos")
                ));
            }
            return piecesData;
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] 해당 방의 기물을 DB에서 조회하는 중 오류가 발생했습니다.", e);
        }
    }
}
