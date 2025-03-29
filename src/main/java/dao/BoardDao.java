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
    private static final String COUNT_POSITION = "SELECT COUNT(*) FROM board WHERE position_row = ? AND position_column = ?";

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
            }
            statement.executeUpdate();
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

        String[] pieceInfo = getPieceInfoByPosition(source);
        try (Connection connection = userDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PIECE)) {
            statement.setInt(1, source.rowValue());
            statement.setInt(2, source.columnValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = userDao.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(COUNT_POSITION)) {
            checkStatement.setInt(1, destination.rowValue());
            checkStatement.setInt(2, destination.columnValue());
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_PIECE)) {
                    deleteStatement.setInt(1, destination.rowValue());
                    deleteStatement.setInt(2, destination.columnValue());
                    deleteStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = userDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PIECE)) {
            statement.setInt(1, destination.rowValue());
            statement.setInt(2, destination.columnValue());
            statement.setString(3, pieceInfo[0]);
            statement.setString(4, pieceInfo[1]);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String[] getPieceInfoByPosition(Position position) {
        String query = "SELECT piece_type, piece_color FROM board WHERE position_row = ? AND position_column = ?";
        try (Connection connection = userDao.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, position.rowValue());
            statement.setInt(2, position.columnValue());
            ResultSet resultSet = statement.executeQuery();
            String pieceType = resultSet.getString("piece_type");
            String pieceColor = resultSet.getString("piece_color");
            return new String[]{pieceType, pieceColor};

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
