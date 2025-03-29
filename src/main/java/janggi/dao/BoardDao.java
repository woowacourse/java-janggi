package janggi.dao;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.TeamColor;
import janggi.dto.BoardDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void save(int roomId, Map<Position, Piece> board) {
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            savePiece(roomId, position, piece);
        }
    }

    public List<BoardDto> selectById(int roomId) {
        final String query = "SELECT position_row, position_col, piece_type, piece_color FROM Board WHERE gameroom_id = ?";
        List<BoardDto> BoardDtos = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BoardDto piece = new BoardDto(
                        resultSet.getInt("position_row"),
                        resultSet.getInt("position_col"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("piece_color")
                );
                BoardDtos.add(piece);
            }
            return BoardDtos;
        } catch (SQLException e) {
            throw new RuntimeException("Board 데이터 가져오기 실패", e);
        }
    }

    public void savePiece(int roomId, Position position, Piece piece) {
        final String query = "INSERT INTO Board (gameroom_id, position_row, position_col, piece_type, piece_color) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomId);

            preparedStatement.setInt(2, position.rowValue());
            preparedStatement.setInt(3, position.columnValue());
            preparedStatement.setString(4, piece.getType().name());
            preparedStatement.setString(5, piece.getColor().name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Piece 정보 저장 실패", e);
        }
    }

    public void update(int roomId, Position source, Position destination, PieceType pieceType,
                       TeamColor teamColor) {
        String deleteSourceQuery = "DELETE FROM Board WHERE gameroom_id = ? AND position_row = ? AND position_col = ?";
        String insertDestinationQuery = "INSERT INTO Board (gameroom_id, position_row, position_col, piece_type, piece_color) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);  // 트랜잭션 시작

            // 1. destination 위치에서 기물 삭제
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSourceQuery)) {
                preparedStatement.setInt(1, roomId);
                preparedStatement.setInt(2, destination.rowValue());
                preparedStatement.setInt(3, destination.columnValue());
                preparedStatement.executeUpdate();
            }

            // 2. destination 위치에 새로운 기물 추가
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertDestinationQuery)) {
                preparedStatement.setInt(1, roomId);
                preparedStatement.setInt(2, destination.rowValue());
                preparedStatement.setInt(3, destination.columnValue());
                preparedStatement.setString(4, pieceType.name());
                preparedStatement.setString(5, teamColor.name());
                preparedStatement.executeUpdate();
            }

            // 3. source 위치에서 기물 삭제
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSourceQuery)) {
                preparedStatement.setInt(1, roomId);
                preparedStatement.setInt(2, source.rowValue());
                preparedStatement.setInt(3, source.columnValue());
                preparedStatement.executeUpdate();
            }

            connection.commit();  // 커밋
        } catch (SQLException e) {
            throw new RuntimeException("Piece Position update 실패", e);
        }
    }
}
