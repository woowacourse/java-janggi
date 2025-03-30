package janggi.db;

import janggi.domain.Team;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {
    private final Connection connection;

    public PieceDao(Connection connection) {
        this.connection = connection;
    }

    public void addPiece(final Piece piece) {
        final var query = "INSERT INTO piece VALUES(NULL, ?, ? ,?, ?)";
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, piece.getPieceType().name());
            preparedStatement.setInt(2, piece.getPosition().x());
            preparedStatement.setInt(3, piece.getPosition().y());
            preparedStatement.setString(4, piece.getTeam().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Piece> readAllPiece() {
        final var query = "SELECT * FROM piece";
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(query)) {
            List<Piece> pieces = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery(query);

            while (resultSet.next()) {
                switch (resultSet.getString("pieceType")) {
                    case "SOLDIER":
                        pieces.add(
                                new Soldier(new Position(resultSet.getInt("positionX"), resultSet.getInt("positionY")),
                                        Team.valueOf(resultSet.getString("team"))));
                        break;
                    case "GUARD":
                        pieces.add(
                                new Guard(new Position(resultSet.getInt("positionX"), resultSet.getInt("positionY")),
                                        Team.valueOf(resultSet.getString("team"))));
                        break;
                    case "CHARIOT":
                        pieces.add(
                                new Chariot(new Position(resultSet.getInt("positionX"), resultSet.getInt("positionY")),
                                        Team.valueOf(resultSet.getString("team"))));
                        break;
                    case "CANNON":
                        pieces.add(
                                new Cannon(new Position(resultSet.getInt("positionX"), resultSet.getInt("positionY")),
                                        Team.valueOf(resultSet.getString("team"))));
                        break;
                    case "ELEPHANT":
                        pieces.add(
                                new Elephant(new Position(resultSet.getInt("positionX"), resultSet.getInt("positionY")),
                                        Team.valueOf(resultSet.getString("team"))));
                        break;
                    case "HORSE":
                        pieces.add(
                                new Horse(new Position(resultSet.getInt("positionX"), resultSet.getInt("positionY")),
                                        Team.valueOf(resultSet.getString("team"))));
                        break;
                    case "GENERAL":
                        pieces.add(
                                new General(new Position(resultSet.getInt("positionX"), resultSet.getInt("positionY")),
                                        Team.valueOf(resultSet.getString("team"))));
                        break;
                }
            }
            return pieces;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllPiece() {
        final var query = "TRUNCATE TABLE piece;";
        try (final var conn = connection.getConnection();
             final var preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
