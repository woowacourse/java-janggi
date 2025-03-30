package domain.dao;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import util.DBConnectionUtil;

public class PieceDaoImpl implements PieceDao {

    private final Long gameId;

    public PieceDaoImpl(Long gameId) {
        this.gameId = gameId;
    }

    public void addAll(final List<Piece> pieces) {
        pieces.forEach(this::add);
    }

    public void add(final Piece piece) {
        final var query = "INSERT INTO piece (y, x, type, team, game_id) VALUES(?, ?, ?, ?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, piece.getPosition().getY());
            preparedStatement.setInt(2, piece.getPosition().getX());
            preparedStatement.setString(3, piece.getType().toString());
            preparedStatement.setString(4, piece.getTeam().toString());
            preparedStatement.setLong(5, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeByPosition(final Position position) {
        final var query = "DELETE FROM piece WHERE y = ? AND x = ? AND game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, position.getY());
            preparedStatement.setInt(2, position.getX());
            preparedStatement.setLong(3, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Piece> findByPosition(final Position position) {
        final var query = "SELECT * FROM piece WHERE y = ? AND x = ? AND game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, position.getY());
            preparedStatement.setInt(2, position.getX());
            preparedStatement.setLong(3, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Piece piece = null;
            if (resultSet.next()) {
                PieceType type = PieceType.find(resultSet.getString("type"));
                Team team = Team.find(resultSet.getString("team"));
                int y = resultSet.getInt("y");
                int x = resultSet.getInt("x");
                piece = new Piece(team, type, new Position(y, x));
            }
            return Optional.ofNullable(piece);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> findAll() {
        final var query = "SELECT * FROM piece WHERE game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setLong(1, gameId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Piece> pieces = new ArrayList<>();
            while (resultSet.next()) {
                PieceType type = PieceType.find(resultSet.getString("type"));
                Team team = Team.find(resultSet.getString("team"));
                int y = resultSet.getInt("y");
                int x = resultSet.getInt("x");
                pieces.add(new Piece(team, type, new Position(y, x)));
            }
            return pieces;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void changePosition(final Position position, final Position newPosition) {
        final var query = "UPDATE piece SET y = ?, x = ? WHERE y = ? AND x = ? AND game_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, newPosition.getY());
            preparedStatement.setInt(2, newPosition.getX());
            preparedStatement.setInt(3, position.getY());
            preparedStatement.setInt(4, position.getX());
            preparedStatement.setLong(5, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.createConnection();
    }
}
