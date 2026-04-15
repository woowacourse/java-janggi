package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardPieceDao {

    public void saveAll(Connection connection, long gameRoomId, List<BoardPieceRawData> pieces) {
        String sql = "INSERT INTO board_piece (game_room_id, row_pos, col_pos, piece_type, team) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (BoardPieceRawData piece : pieces) {
                statement.setLong(1, gameRoomId);
                statement.setInt(2, piece.rowPos());
                statement.setInt(3, piece.colPos());
                statement.setString(4, piece.pieceType());
                statement.setString(5, piece.team());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException("기물 저장 실패", e);
        }
    }

    public void insertPiece(Connection connection, long gameRoomId, BoardPieceRawData piece) {
        String sql = "INSERT INTO board_piece (game_room_id, row_pos, col_pos, piece_type, team) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameRoomId);
            statement.setInt(2, piece.rowPos());
            statement.setInt(3, piece.colPos());
            statement.setString(4, piece.pieceType());
            statement.setString(5, piece.team());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("기물 저장 실패", e);
        }
    }

    public void deletePieceAt(Connection connection, long gameRoomId, BoardPieceRawData piece) {
        String sql = "DELETE FROM board_piece WHERE game_room_id = ? AND row_pos = ? AND col_pos = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameRoomId);
            statement.setInt(2, piece.rowPos());
            statement.setInt(3, piece.colPos());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("기물 삭제 실패", e);
        }
    }

    public List<BoardPieceRawData> findByGameRoomId(Connection connection, long gameRoomId) {
        String sql = "SELECT row_pos, col_pos, piece_type, team FROM board_piece WHERE game_room_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameRoomId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<BoardPieceRawData> pieces = new ArrayList<>();
                while (resultSet.next()) {
                    pieces.add(new BoardPieceRawData(
                            resultSet.getInt("row_pos"),
                            resultSet.getInt("col_pos"),
                            resultSet.getString("piece_type"),
                            resultSet.getString("team")
                    ));
                }
                return pieces;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("기물 조회 실패", e);
        }
    }

    public void deleteByGameRoomId(Connection connection, long gameRoomId) {
        String sql = "DELETE FROM board_piece WHERE game_room_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameRoomId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("기물 삭제 실패", e);
        }
    }
}
