package repository.dao;

import janggi.piece.Piece;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import repository.connection.ConnectDatabase;
import repository.converter.PieceConverter;

public class PieceDao {

    private final ConnectDatabase connectDatabase;

    public PieceDao(ConnectDatabase connectDatabase) {
        this.connectDatabase = connectDatabase;
    }

    public void addAll(final Set<PieceConverter> pieceConverters) {
        final var query = "INSERT INTO PIECE (row_index, column_index, piece_type_name, team_name) VALUES(?, ?, ?, ?)";

        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (PieceConverter pieceConverter : pieceConverters) {
                preparedStatement.setString(1, pieceConverter.rowIndex());
                preparedStatement.setString(2, pieceConverter.columnIndex());
                preparedStatement.setString(3, pieceConverter.pieceTypeName());
                preparedStatement.setString(4, pieceConverter.teamName());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Piece> findAll() {
        Set<Piece> pieces = new HashSet<>();

        final var query = "SELECT * FROM PIECE";

        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pieces.add(PieceConverter.from(
                        resultSet.getString("row_index"),
                        resultSet.getString("column_index"),
                        resultSet.getString("piece_type_name"),
                        resultSet.getString("team_name"))
                );
            }

            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieces;
    }

    public void deleteAll() {
        final var query = "DELETE FROM PIECE";

        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeUpdate();
            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
