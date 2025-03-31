package dao;

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
}
