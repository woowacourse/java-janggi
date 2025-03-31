package dao;

import domain.board.BoardPoint;
import entity.BoardEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {

    private final JanggiConnection janggiConnection;

    public BoardDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    public List<BoardEntity> getBoardEntities() {
        final var query = "SELECT * FROM board";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<BoardEntity> boardEntities = new ArrayList<>();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                int rowIndex = resultSet.getInt("row_index");
                int columnIndex = resultSet.getInt("column_index");
                long pieceId = resultSet.getLong("piece_id");

                BoardEntity boardEntity = new BoardEntity(id, rowIndex, columnIndex, pieceId);
                boardEntities.add(boardEntity);
            }

            return boardEntities;

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BoardEntity findByBoardPoint(BoardPoint boardPoint) {

        final var query = "SELECT * FROM board WHERE row_index = ? AND column_index = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, boardPoint.row());
            preparedStatement.setInt(2, boardPoint.column());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                int rowIndex = resultSet.getInt("row_index");
                int columnIndex = resultSet.getInt("column_index");
                long pieceId = resultSet.getLong("piece_id");

                return new BoardEntity(id, rowIndex, columnIndex, pieceId);
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void delete(BoardEntity boardEntity) {

        final var query = "DELETE from board WHERE id = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, boardEntity.getId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void save(BoardPoint boardPoint, long pieceId) {
        final var query = "INSERT INTO board (piece_id, row_index, column_index) "
                + "VALUES (?, ?, ?);";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, pieceId);
            preparedStatement.setInt(2, boardPoint.row());
            preparedStatement.setInt(3, boardPoint.column());
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePiece(BoardPoint arrivalBoardPoint, long pieceId) {
        final var query = "UPDATE board SET piece_id = ? WHERE row_index = ? and column_index = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, String.valueOf(pieceId));
            preparedStatement.setString(2, String.valueOf(arrivalBoardPoint.row()));
            preparedStatement.setString(3, String.valueOf(arrivalBoardPoint.column()));
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
