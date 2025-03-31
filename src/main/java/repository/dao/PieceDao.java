package repository.dao;

import janggi.piece.Piece;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import repository.connection.ConnectDatabase;
import repository.connection.ConnectMysql;
import repository.entity.PieceEntity;

public class PieceDao {

    public void addPiece(final PieceEntity pieceEntity) {
        final var query = "INSERT INTO PIECE (row_index, column_index, piece_type_name, team_name) VALUES(?, ?, ?, ?)";

        ConnectDatabase connectDatabase = new ConnectMysql();
        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, pieceEntity.rowIndex());
            preparedStatement.setString(2, pieceEntity.columnIndex());
            preparedStatement.setString(3, pieceEntity.pieceTypeName());
            preparedStatement.setString(4, pieceEntity.teamName());
            preparedStatement.executeUpdate();

            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Piece> findByPiece() {
        Set<Piece> pieces = new HashSet<>();

        final var query = "SELECT * FROM PIECE";

        ConnectDatabase connectDatabase = new ConnectMysql();
        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pieces.add(PieceEntity.from(
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

        ConnectDatabase connectDatabase = new ConnectMysql();
        try (Connection connection = connectDatabase.create();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeUpdate();
            connectDatabase.close(connection);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
