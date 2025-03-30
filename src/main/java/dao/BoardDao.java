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
    private static final String SELECT_TYPE_COLOR = "SELECT piece_type, piece_color FROM board WHERE position_row = ? AND position_column = ?";

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
            // 이동할 기물 정보 가져오기
            String[] pieceInfo = getPieceInfoByPosition(source, connection);

            connection.setAutoCommit(false);

            try {
                try (PreparedStatement deleteSource = connection.prepareStatement(DELETE_PIECE)) {
                    deleteSource.setInt(1, source.rowValue());
                    deleteSource.setInt(2, source.columnValue());
                    deleteSource.executeUpdate();
                }

                try (PreparedStatement deleteDestination = connection.prepareStatement(DELETE_PIECE)) {
                    deleteDestination.setInt(1, destination.rowValue());
                    deleteDestination.setInt(2, destination.columnValue());
                    deleteDestination.executeUpdate();
                }

                try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_PIECE)) {
                    insertStatement.setInt(1, destination.rowValue());
                    insertStatement.setInt(2, destination.columnValue());
                    insertStatement.setString(3, pieceInfo[0]);
                    insertStatement.setString(4, pieceInfo[1]);
                    insertStatement.executeUpdate();
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

    private String[] getPieceInfoByPosition(Position position, Connection connection) throws SQLException {
        String[] pieceInfo = new String[2];
        try (PreparedStatement statement = connection.prepareStatement(SELECT_TYPE_COLOR)) {
            statement.setInt(1, position.rowValue());
            statement.setInt(2, position.columnValue());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    pieceInfo[0] = resultSet.getString("piece_type");
                    pieceInfo[1] = resultSet.getString("piece_color");
                } else {
                    throw new SQLException("해당 위치에 기물이 없습니다: " + position);
                }
            }
        }
        return pieceInfo;
    }
}
