package janggi.infra.dao;

import janggi.infra.dto.PieceData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PiecesDao {

    public void save(Long roomId, List<PieceData> data, Connection connection) throws SQLException {
        String sql = "INSERT INTO piece (game_room_id, piece_name, team, row_pos, col_pos) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pieceStatement = connection.prepareStatement(sql);
        for (PieceData piece : data) {
            pieceStatement.setLong(1, roomId);
            pieceStatement.setString(2, piece.pieceName());
            pieceStatement.setString(3, piece.teamName());
            pieceStatement.setInt(4, piece.row());
            pieceStatement.setInt(5, piece.column());
            pieceStatement.executeUpdate();
        }
    }

    public void delete(Long roomId, int row, int col, Connection connection) throws SQLException {
        String sql = "DELETE FROM piece WHERE game_room_id = ? AND row_pos = ? AND col_pos = ?";
        PreparedStatement killStatement = connection.prepareStatement(sql);
        killStatement.setLong(1, roomId);
        killStatement.setInt(2, row);
        killStatement.setInt(3, col);
        killStatement.executeUpdate();
    }

    public void update(Long roomId, int fromRow, int fromCol, int toRow, int toCol, Connection connection) throws SQLException {
        String movePieceSql = "UPDATE piece SET row_pos = ?, col_pos = ? WHERE game_room_id = ? AND row_pos = ? AND col_pos = ?";
        PreparedStatement moveStatement = connection.prepareStatement(movePieceSql);
        moveStatement.setInt(1, toRow);
        moveStatement.setInt(2, toCol);
        moveStatement.setLong(3, roomId);
        moveStatement.setInt(4, fromRow);
        moveStatement.setInt(5, fromCol);
        moveStatement.executeUpdate();
    }

    public List<PieceData> findAllByRoomId(Long roomId, Connection connection) {
        String sql = "SELECT piece_name, team, row_pos, col_pos FROM piece WHERE game_room_id = ?";
        List<PieceData> piecesData = new ArrayList<>();
        try {
            PreparedStatement pieceStatement = connection.prepareStatement(sql);
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
            throw new RuntimeException(e);
        }
    }
}
