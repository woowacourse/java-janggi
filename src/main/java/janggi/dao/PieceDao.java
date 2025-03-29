package janggi.dao;

import janggi.piece.Piece;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PieceDao {

    private final int id;
    private Piece piece;
    private final GameDao gameDao;

    public PieceDao(int id, Piece piece, GameDao gameDao) {
        this.id = id;
        this.piece = piece;
        this.gameDao = gameDao;
    }

    public static PieceDao createPiece(Piece piece, GameDao gameDao) {
        final var createQuery = "INSERT INTO piece (name,is_running,row_index,column_index,team,game_id) VALUES(?,?,?,?,?,?)";
        final var checkQuery = "SELECT * FROM piece WHERE game_id=? AND row_index=? AND column_index=?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedCreateStatement = connection.prepareStatement(createQuery);
             final var preparedCheckStatement = connection.prepareStatement(checkQuery)) {
            preparedCreateStatement.setString(1, piece.getType().name());
            preparedCreateStatement.setBoolean(2, true); //TINYINT : 1
            preparedCreateStatement.setInt(3, piece.getPoint().row());
            preparedCreateStatement.setInt(4, piece.getPoint().column());
            preparedCreateStatement.setString(5, piece.getTeam().name());
            preparedCreateStatement.setInt(6, gameDao.getId());
            preparedCreateStatement.executeUpdate();

            preparedCheckStatement.setInt(1, gameDao.getId());
            preparedCheckStatement.setInt(2, piece.getPoint().row());
            preparedCheckStatement.setInt(3, piece.getPoint().column());
            ResultSet resultSet = preparedCheckStatement.executeQuery();
            if (resultSet.next()) {
                return new PieceDao(resultSet.getInt("id"), piece, gameDao);
            }
            throw new IllegalStateException("기물이 생성되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePiecePoint(Piece newPiece) {
        final var query = "UPDATE piece SET row_index=?, column_index=? WHERE team=? ";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newPiece.getPoint().row());
            preparedStatement.setInt(2, newPiece.getPoint().column());
            preparedStatement.setString(3, newPiece.getTeam().name());
            preparedStatement.executeUpdate();

            this.piece = newPiece;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateToAttacked() {
        final var query = "UPDATE piece SET is_running=? WHERE id=? ";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Piece getPiece() {
        return piece;
    }
}
