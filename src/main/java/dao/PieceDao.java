package dao;

import domain.piece.MovementRule;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private final ConnectionManager connectionManager;

    public PieceDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void save(final Piece piece, final Player player) {
        final var query = "INSERT INTO piece(`row`, `column`, team, piece_type) VALUES(?, ?, ?, ?)";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piece.getPosition().getRow());
            preparedStatement.setInt(2, piece.getPosition().getColumn());
            preparedStatement.setString(3, player.team().name());
            preparedStatement.setString(4, piece.getType().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> findAllByTeam(final Team team) {
        List<Piece> pieces = new ArrayList<>();

        final var query = "SELECT * FROM piece WHERE team = ?";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, team.name());
            final var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int row = resultSet.getInt("row");
                int column = resultSet.getInt("column");
                PieceType type = PieceType.getValue(resultSet.getString("piece_type"));

                pieces.add(getPiece(Position.of(row, column), type, team));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return pieces;
    }

    public void updatePosition(final Piece piece, final Position targetPosition) {
        final var query = "UPDATE piece SET `row` = ?, `column` = ? "
                + "WHERE `row` = ? AND `column` = ?";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, targetPosition.getRow());
            preparedStatement.setInt(2, targetPosition.getColumn());
            preparedStatement.setInt(3, piece.getPosition().getRow());
            preparedStatement.setInt(4, piece.getPosition().getColumn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(final Piece piece, final Team team) {
        final var query = "DELETE FROM piece WHERE `row` = ? AND `column` = ? AND piece_type = ? AND team = ?";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piece.getPosition().getRow());
            preparedStatement.setInt(2, piece.getPosition().getColumn());
            preparedStatement.setString(3, piece.getType().name());
            preparedStatement.setString(4, team.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        final var query = "DELETE FROM piece";
        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Piece getPiece(final Position position, final PieceType type, final Team team) {
        if (type == PieceType.CANNON) {
            return new Piece(position, type, MovementRule.CANNON);
        }
        if (type == PieceType.CHARIOT) {
            return new Piece(position, type, MovementRule.CHARIOT);
        }
        if (type == PieceType.ELEPHANT) {
            return new Piece(position, type, MovementRule.ELEPHANT);
        }
        if (type == PieceType.GENERAL && team == Team.HAN) {
            return new Piece(position, type, MovementRule.HAN_GENERAL);
        }
        if (type == PieceType.GENERAL && team == Team.CHO) {
            return new Piece(position, type, MovementRule.CHO_GENERAL);
        }
        if (type == PieceType.GUARD && team == Team.HAN) {
            return new Piece(position, type, MovementRule.HAN_GUARD);
        }
        if (type == PieceType.GUARD && team == Team.CHO) {
            return new Piece(position, type, MovementRule.CHO_GUARD);
        }
        if (type == PieceType.HORSE) {
            return new Piece(position, type, MovementRule.HORSE);
        }
        if (type == PieceType.SOLDIER && team == Team.HAN) {
            return new Piece(position, type, MovementRule.HAN_SOLDIER);
        }
        if (type == PieceType.SOLDIER && team == Team.CHO) {
            return new Piece(position, type, MovementRule.CHO_SOLDIER);
        }
        throw new IllegalArgumentException("[ERROR] 데이터베이스에 존재하지 않는 기물입니다.");
    }
}
