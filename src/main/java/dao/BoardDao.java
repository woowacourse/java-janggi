package dao;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    private static final String INSERT_PIECE = "INSERT INTO board (position_row, position_column, piece_type, piece_color) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BOARD = "SELECT position_row, position_column, piece_type, piece_color FROM board";
    private static final String DELETE_PIECE = "DELETE FROM board WHERE position_row = ? AND position_column = ?";
    private static final String UPDATE_POSITION = "UPDATE board SET position_row = ?, position_column = ? WHERE position_row = ? AND position_column = ?";
    private static final String DELETE_BOARD = "DELETE FROM board";

    private final Executor databaseExecutor;

    public BoardDao(Executor databaseExecutor) {
        this.databaseExecutor = databaseExecutor;
    }

    public void saveBoard(Map<Position, Piece> board) {
        databaseExecutor.executeBatch(INSERT_PIECE, statement -> {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                statement.setInt(1, position.rowValue());
                statement.setInt(2, position.columnValue());
                statement.setString(3, piece.getType().toString());
                statement.setString(4, piece.getColor().toString());
                statement.addBatch();
            }
        });
    }

    public Map<Position, Piece> loadBoard() {
        return databaseExecutor.executeQuery(SELECT_BOARD, (statement, resultSet) -> {
            Map<Position, Piece> board = new HashMap<>();
            while (resultSet.next()) {
                int row = resultSet.getInt("position_row");
                int column = resultSet.getInt("position_column");
                String type = resultSet.getString("piece_type");
                String color = resultSet.getString("piece_color");

                Position position = new Position(Row.from(row), Column.from(column));
                PieceType pieceType = PieceType.valueOf(type);
                PieceColor pieceColor = PieceColor.valueOf(color);
                Piece piece = PieceFactory.createPiece(pieceType, pieceColor);
                board.put(position, piece);
            }
            return board;
        });
    }

    public void updatePosition(Position source, Position destination) {
        databaseExecutor.executeTransaction(connection -> {
            databaseExecutor.executeUpdate(DELETE_PIECE, statement -> {
                statement.setInt(1, destination.rowValue());
                statement.setInt(2, destination.columnValue());
            });
            databaseExecutor.executeUpdate(UPDATE_POSITION, statement -> {
                statement.setInt(1, destination.rowValue());
                statement.setInt(2, destination.columnValue());
                statement.setInt(3, source.rowValue());
                statement.setInt(4, source.columnValue());
            });
        });
    }

    public void deleteBoard() {
        databaseExecutor.executeUpdate(DELETE_BOARD, (statement) -> {
        });
    }
}
