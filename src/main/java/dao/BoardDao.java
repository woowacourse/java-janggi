package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class BoardDao {
    public void deleteBoardEntity() {
        final var query = "DELETE FROM Board";

        try (final var connection = Connector.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPieceEntitiesToBoardEntity(final List<PieceEntity> pieceEntities) {
        pieceEntities.forEach(this::addPieceEntityToBoardEntity);
    }

    private void addPieceEntityToBoardEntity(final PieceEntity pieceEntity) {
        final var query = "INSERT INTO Board VALUES(?, ?, ?, ?)";

        try (final var connection = Connector.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pieceEntity.row());
                preparedStatement.setInt(2, pieceEntity.column());
                preparedStatement.setString(3, pieceEntity.type());
                preparedStatement.setString(4, pieceEntity.dynasty());

                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PieceEntity> readPieceEntitiesFromBoardEntity() {
        final var query = "SELECT row_value, column_value, type, dynasty FROM Board";
        final List<PieceEntity> pieceEntities = new ArrayList<>();

        try (final var connection = Connector.getConnection()) {
            if (connection == null) {
                throw new SQLException("데이터 베이스 연결에 실패했습니다.");
            }
            try (final var statement = connection.createStatement();
                 final var resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    final int row = resultSet.getInt("row_value");
                    final int column = resultSet.getInt("column_value");
                    final String type = resultSet.getString("type");
                    final String dynasty = resultSet.getString("dynasty");

                    pieceEntities.add(new PieceEntity(row, column, type, dynasty));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieceEntities;
    }
}
