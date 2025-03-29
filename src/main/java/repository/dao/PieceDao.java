package repository.dao;

import java.sql.SQLException;
import repository.connection.ConnectMysql;
import repository.entity.PieceEntity;

public class PieceDao {

    public void addPiece(final PieceEntity pieceEntity) {
        final var query = "INSERT INTO PIECE (row_index, column_index, piece_type_name, team_name) VALUES(?, ?, ?, ?)";
        try (
                final var connection = new ConnectMysql().create();
                final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, pieceEntity.rowIndex());
            preparedStatement.setString(2, pieceEntity.columnIndex());
            preparedStatement.setString(3, pieceEntity.pieceTypeName());
            preparedStatement.setString(4, pieceEntity.teamName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
