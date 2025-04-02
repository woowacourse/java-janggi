package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class BoardDao {
    public void deleteBoardDB() {
        final var query = "DELETE FROM Board";

        try (final var connection = Connector.getConnection()) {
            try (final var statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBoardDB(final List<PieceInfo> pieceEntities) {
        pieceEntities.forEach(this::insertPieceInfoToBoardDB);
    }

    private void insertPieceInfoToBoardDB(final PieceInfo pieceInfo) {
        final var query = "INSERT INTO Board VALUES(?, ?, ?, ?)";

        try (final var connection = Connector.getConnection()) {
            try (final var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pieceInfo.row());
                preparedStatement.setInt(2, pieceInfo.column());
                preparedStatement.setString(3, pieceInfo.type());
                preparedStatement.setString(4, pieceInfo.dynasty());

                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PieceInfo> readBoardDB() {
        final var query = "SELECT row_value, column_value, type, dynasty FROM Board";
        final List<PieceInfo> pieceEntities = new ArrayList<>();

        try (final var connection = Connector.getConnection()) {
            try (final var statement = connection.createStatement();
                 final var resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    final int row = resultSet.getInt("row_value");
                    final int column = resultSet.getInt("column_value");
                    final String type = resultSet.getString("type");
                    final String dynasty = resultSet.getString("dynasty");

                    pieceEntities.add(new PieceInfo(row, column, type, dynasty));
                }
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieceEntities;
    }
}
