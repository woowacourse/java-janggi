package dao;

import domain.janggiPiece.Piece;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;
import entity.PieceEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PieceDaoImpl implements PieceDao {
    private final DatabaseConnector databaseConnector;

    public PieceDaoImpl(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public List<PieceEntity> findAll() {
        final var query = "SELECT * FROM piece";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            List<PieceEntity> result = new ArrayList<>();
            while (resultSet.next()) {
                final int row = resultSet.getInt("position_row");
                final int col = resultSet.getInt("position_col");
                String typeName = resultSet.getString("piece_type");
                String teamName = resultSet.getString("team");
                result.add(new PieceEntity(row, col, JanggiTeam.from(teamName), Piece.from(typeName)));
            }
            return result;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PieceEntity> findByPosition(JanggiPosition position) {
        final var query = "SELECT * FROM piece WHERE position_row = ? AND position_col = ?";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.getRow());
            preparedStatement.setInt(2, position.getCol());
            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String typeName = resultSet.getString("piece_type");
                String teamName = resultSet.getString("team");
                PieceEntity pieceEntity = new PieceEntity(position.getRow(), position.getCol(), JanggiTeam.from(teamName), Piece.from(typeName));
                return Optional.of(pieceEntity);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void deleteByPosition(JanggiPosition position) {
        final var query = "DELETE FROM piece WHERE position_row=? AND position_col=?";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, position.getRow());
            preparedStatement.setInt(2, position.getCol());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final var query = "TRUNCATE piece";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(PieceEntity pieceEntity) {
        final var query = "INSERT INTO piece(position_row, position_col, piece_type, team) VALUES (?, ?, ?, ?)";
        try (final var connection = databaseConnector.getConnection();
             final var preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, pieceEntity.row());
            preparedStatement.setInt(2, pieceEntity.col());
            preparedStatement.setString(3, pieceEntity.getPieceName());
            preparedStatement.setString(4, pieceEntity.getTeamName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
