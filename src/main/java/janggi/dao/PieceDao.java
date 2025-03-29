package janggi.dao;

import janggi.game.Game;
import janggi.piece.Piece;
import java.sql.SQLException;

public class PieceDao {

    public void createPiece(Piece piece, GameDao gameDao) {
        final var query = "INSERT INTO piece (name,is_running,row_index,column_index,team,game_id) VALUES(?,?,?,?,?,?)";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, piece.getName());
            preparedStatement.setBoolean(2, true);
            preparedStatement.setInt(3, piece.getPoint().row());
            preparedStatement.setInt(4, piece.getPoint().column());
            preparedStatement.setString(5, piece.getTeam().name());
            preparedStatement.setInt(6, gameDao.getId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
