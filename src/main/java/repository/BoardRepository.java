package repository;

import domain.Board;
import domain.Camp;
import domain.PieceType;
import domain.Position;
import domain.pieces.Piece;
import dto.BoardStatusDto;
import dto.PositionStatusDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepository {

    private final String URL = "jdbc:mysql://localhost:3306/janggi";
    private final String USER = "root";
    private final String PASSWORD = "1020";

    public void createBoard(Long gameId, Board board) {
        String sql = "INSERT INTO board (game_id, col_pos, row_pos, piece_type, camp) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                BoardStatusDto boardToStore = board.getBoardStatus();
                for (List<PositionStatusDto> row : boardToStore.positionStatusDtos()) {
                    for (PositionStatusDto col : row) {
                        preparedStatement.setLong(1, gameId);
                        preparedStatement.setInt(2, col.position().getCol());
                        preparedStatement.setInt(3, col.position().getRow());
                        preparedStatement.setString(4, col.pieceType().name());
                        preparedStatement.setString(5, col.camp().name());

                        preparedStatement.addBatch();
                    }
                }
                preparedStatement.executeBatch();
            }

        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }

    }

    public void updateBoard(Long gameId, Piece movingPiece, Position fromPosition,
            Position toPosition) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String deleteSql = "UPDATE board SET piece_type=?, camp=? WHERE game_id=? AND col_pos=? AND row_pos=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setString(1, PieceType.NONE.name());
                preparedStatement.setString(2, Camp.NONE.name());
                preparedStatement.setLong(3, gameId);
                preparedStatement.setInt(4, fromPosition.getCol());
                preparedStatement.setInt(5, fromPosition.getRow());
                preparedStatement.executeUpdate();
            }

            String sql = "UPDATE board SET piece_type=?, camp=? WHERE game_id=? AND col_pos=? AND row_pos=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1,
                        movingPiece.getPieceType().name());
                preparedStatement.setString(2, movingPiece.getCamp().name());
                preparedStatement.setLong(3, gameId);
                preparedStatement.setInt(4, toPosition.getCol());
                preparedStatement.setInt(5, toPosition.getRow());
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }

    public Board findBoard(Long gameId) {
        String sql = "SELECT col_pos, row_pos, piece_type, camp FROM board WHERE game_id=?";
        try (Connection connection = DriverManager.getConnection(URL, USER,
                PASSWORD); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, gameId);

            try (java.sql.ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Position, Piece> board = new HashMap<>();
                while (resultSet.next()) {
                    int colPos = resultSet.getInt("col_pos");
                    int rowPos = resultSet.getInt("row_pos");
                    String pieceTypeStr = resultSet.getString("piece_type");
                    String campStr = resultSet.getString("camp");
                    Position position = new Position(colPos, rowPos);
                    PieceType pieceType = PieceType.valueOf(pieceTypeStr);
                    if (pieceType.equals(PieceType.NONE)) {
                        continue;
                    }
                    Camp camp = Camp.valueOf(campStr);
                    Piece piece = Piece.of(pieceType, camp);

                    board.put(position, piece);
                }
                return Board.restore(board);
            }

        } catch (SQLException e) {
            throw new RuntimeException("db 오류", e);
        }
    }
}
