package janggi.dao.piece;

import janggi.dao.JanggiDatabase;
import janggi.dao.entity.PieceEntity;
import janggi.piece.PieceType;
import janggi.piece.Team;
import janggi.position.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDaoImpl implements PieceDao {

    @Override
    public void addPieces(final List<PieceEntity> pieceEntities) {
        final String query = "INSERT INTO piece (piece_type, team, row_index, col_index, game_id ) VALUES (?, ?, ?, ?, ?)";

        try (final Connection conn = JanggiDatabase.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {

            for (final PieceEntity pieceEntity : pieceEntities) {
                stmt.setString(1, pieceEntity.getPieceType().getValue());
                stmt.setString(2, pieceEntity.getTeam().getDescription());
                stmt.setInt(3, pieceEntity.getRowIndex());
                stmt.setInt(4, pieceEntity.getColIndex());
                stmt.setLong(5, pieceEntity.getGameId());

                stmt.executeUpdate();
            }

        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PieceEntity> findPiecesById(final Long gameId) {
        final String query = "SELECT * FROM piece WHERE game_id = ?";
        final List<PieceEntity> pieces = new ArrayList<>();

        try (final var connection = JanggiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, gameId);
            final var resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pieces.add(new PieceEntity(
                        resultSet.getLong("id"),
                        PieceType.from(resultSet.getString("piece_type")),
                        Team.from(resultSet.getString("team")),
                        resultSet.getInt("row_index"),
                        resultSet.getInt("col_index"),
                        resultSet.getLong("game_id")
                ));
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieces;
    }

    @Override
    public void removePieceByPosition(final Long gameId, final Position position) {
        final String query = "DELETE FROM piece WHERE game_id = ? AND row_index = ? AND col_index = ?";

        try (final Connection conn = JanggiDatabase.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, gameId);
            stmt.setInt(2, position.row());
            stmt.setInt(3, position.col());

            stmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePiece(final PieceEntity pieceEntity) {
        final String query = "INSERT INTO piece (piece_type, team, row_index, col_index, game_id) VALUES (?, ?, ?, ?, ?)";

        try (final Connection conn = JanggiDatabase.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pieceEntity.getPieceType().getValue());
            stmt.setString(2, pieceEntity.getTeam().getDescription());
            stmt.setInt(3, pieceEntity.getRowIndex());
            stmt.setInt(4, pieceEntity.getColIndex());
            stmt.setLong(5, pieceEntity.getGameId());

            stmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePiecesBy(final Long gameId) {
        final String query = "DELETE FROM piece WHERE game_id = ?";

        try (final Connection conn = JanggiDatabase.getConnection();
             final PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, gameId);
            stmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
