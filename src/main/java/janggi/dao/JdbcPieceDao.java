package janggi.dao;

import static janggi.dao.ConnectionUtils.getConnection;

import janggi.dao.entity.PieceEntity;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Point;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPieceDao implements PieceDao {

    @Override
    public void addPieces(List<PieceEntity> pieces) {
        final var query = "INSERT INTO piece (x, y, dynasty, piece_type, game_id) VALUES(?, ?, ?, ?, ?)";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (PieceEntity piece : pieces) {
                preparedStatement.setInt(1, piece.getPoint().x());
                preparedStatement.setInt(2, piece.getPoint().y());
                preparedStatement.setString(3, piece.getDynasty().name());
                preparedStatement.setString(4, piece.getPieceType().name());
                preparedStatement.setLong(5, piece.getGameId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }

    @Override
    public List<PieceEntity> findAllByGameId(Long gameId) {
        final var query = "SELECT * FROM piece WHERE game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, gameId);

            final var resultSet = preparedStatement.executeQuery();
            List<PieceEntity> pieceEntities = new ArrayList<>();
            while (resultSet.next()) {
                PieceEntity pieceEntity = new PieceEntity(
                        resultSet.getLong("id"),
                        new Point(resultSet.getInt("x"), resultSet.getInt("y")),
                        Dynasty.valueOf(resultSet.getString("dynasty")),
                        PieceType.valueOf(resultSet.getString("piece_type")),
                        resultSet.getLong("game_id")
                );
                pieceEntities.add(pieceEntity);
            }
            return pieceEntities;
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }

    @Override
    public void updatePiece(Long gameId, Point from, Point to) {
        final var query = "UPDATE piece SET x = ?, y = ? WHERE x = ? AND y = ? AND game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, to.x());
            preparedStatement.setInt(2, to.y());
            preparedStatement.setInt(3, from.x());
            preparedStatement.setInt(4, from.y());
            preparedStatement.setLong(5, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }

    @Override
    public void deletePiece(Long gameId, Point point) {
        final var query = "DELETE FROM piece WHERE x = ? AND y = ? AND game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.x());
            preparedStatement.setInt(2, point.y());
            preparedStatement.setLong(3, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new DatabaseSQLException(e);
        }
    }
}
