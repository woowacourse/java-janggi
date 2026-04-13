package repository;

import domain.Board;
import domain.Camp;
import domain.PieceType;
import domain.Position;
import domain.pieces.Piece;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BoardRepository {



    private final String URL = "jdbc:mysql://localhost:3306/janggi";
    private final String USER = "root";
    private final String PASSWORD = "1020";
    public void updateBoard(Long gameId, Board board) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String deleteSql = "DELETE FROM board WHERE game_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql)) {
                preparedStatement.setLong(1, gameId);
                preparedStatement.executeUpdate();
            }

            String sql = "INSERT INTO board (game_id, col_pos, row_pos, piece_type, camp) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                Map<Position, Piece> boardToStore = board.getBoard();
                for (Entry<Position, Piece> positionPieceEntry : boardToStore.entrySet()) {
                    Position position = positionPieceEntry.getKey();
                    Piece piece = positionPieceEntry.getValue();
                    preparedStatement.setLong(1, gameId);
                    preparedStatement.setInt(2, position.getCol());
                    preparedStatement.setInt(3, position.getRow());
                    preparedStatement.setString(4, piece.getPieceType().name());
                    preparedStatement.setString(5, piece.getCamp().name());

                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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
