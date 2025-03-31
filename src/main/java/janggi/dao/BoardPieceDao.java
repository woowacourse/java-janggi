package janggi.dao;

import janggi.entity.BoardPieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class BoardPieceDao {
    private final Connection connection;

    public BoardPieceDao(Connection connection) {
        this.connection = connection;
    }

    public List<BoardPieceEntity> selectById(int roomId) {
        final String query = "SELECT id, gameroom_id, position_row, position_col, piece_type, piece_color FROM BoardPiece WHERE gameroom_id = ?";
        List<BoardPieceEntity> boardPieceEntities = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BoardPieceEntity pieceEntity = new BoardPieceEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("gameroom_id"),
                        resultSet.getInt("position_row"),
                        resultSet.getInt("position_col"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("piece_color")
                );
                boardPieceEntities.add(pieceEntity);
            }
            return boardPieceEntities;
        } catch (SQLException e) {
            throw new RuntimeException("Board 데이터 가져오기 실패", e);
        }
    }

    public Optional<BoardPieceEntity> selectByPosition(int roomId, int positionRow, int positionCol) {
        final String query = "SELECT id, gameroom_id, position_row, position_col, piece_type, piece_color FROM BoardPiece WHERE gameroom_id = ? AND position_row = ? AND position_col = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, positionRow);
            preparedStatement.setInt(3, positionCol);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new BoardPieceEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("gameroom_id"),
                        resultSet.getInt("position_row"),
                        resultSet.getInt("position_col"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("piece_color")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Board 데이터 가져오기 실패", e);
        }
    }

    public void insert(BoardPieceEntity entity) {
        final String query = "INSERT INTO BoardPiece (gameroom_id, position_row, position_col, piece_type, piece_color) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,entity.getGameRoomId());

            preparedStatement.setInt(2, entity.getPositionRow());
            preparedStatement.setInt(3, entity.getPositionCol());
            preparedStatement.setString(4, entity.getPieceType());
            preparedStatement.setString(5, entity.getPieceColor());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Piece 정보 저장 실패", e);
        }
    }

    public void deleteByPosition(int roomId, int row, int col) {
        String query = "DELETE FROM BoardPiece WHERE gameroom_id = ? AND position_row = ? AND position_col = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, roomId);
            ps.setInt(2, row);
            ps.setInt(3, col);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("BoardPieceEntity 삭제 실패", e);
        }
    }

    public void update(BoardPieceEntity entity) {
        String query = "UPDATE BoardPiece SET position_row = ?, position_col = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getPositionRow());
            ps.setInt(2, entity.getPositionCol());
            ps.setLong(3, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("BoardPieceEntity 업데이트 실패", e);
        }
    }
}
