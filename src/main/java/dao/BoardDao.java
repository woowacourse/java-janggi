package dao;

import domain.board.Column;
import domain.board.Position;
import domain.board.Row;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceFactory;
import domain.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {

    private static final String INSERT_PIECE = "INSERT INTO board (position_row, position_column, piece_type, piece_color) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BOARD = "SELECT position_row, position_column, piece_type, piece_color FROM board";
    private static final String DELETE_PIECE = "DELETE FROM board WHERE position_row = ? AND position_column = ?";
    private static final String UPDATE_POSITION = "UPDATE board SET position_row = ?, position_column = ? WHERE position_row = ? AND position_column = ?";
    private static final String DELETE_BOARD = "DELETE FROM board";

    private final UserDao userDao;

    public BoardDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveBoard(Map<Position, Piece> board) {
        try (Connection connection = userDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PIECE)) {
            for (Map.Entry<Position, Piece> entry : board.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                statement.setInt(1, position.rowValue());
                statement.setInt(2, position.columnValue());
                statement.setString(3, piece.getType().toString());
                statement.setString(4, piece.getColor().toString());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Position, Piece> loadBoard() {
        Map<Position, Piece> board = new HashMap<>();
        try (Connection connection = userDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BOARD);
             ResultSet resultSet = statement.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return board;
    }

    public void updatePosition(Position source, Position destination) {
        try (Connection connection = userDao.getConnection()) {
            connection.setAutoCommit(false);
            try {
                try (PreparedStatement deleteDestination = connection.prepareStatement(DELETE_PIECE)) {
                    deleteDestination.setInt(1, destination.rowValue());
                    deleteDestination.setInt(2, destination.columnValue());
                    deleteDestination.executeUpdate();
                }

                try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE_POSITION)) {
                    updateStatement.setInt(1, destination.rowValue()); // 새로운 위치
                    updateStatement.setInt(2, destination.columnValue());
                    updateStatement.setInt(3, source.rowValue()); // 기존 위치
                    updateStatement.setInt(4, source.columnValue());
                    updateStatement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBoard() {
        try (Connection connection = userDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BOARD)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
