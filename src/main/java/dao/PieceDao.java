package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import model.piece.PieceType;
import model.piece.Team;
import model.piece.Piece;
import model.position.Column;
import model.position.Position;
import model.position.Row;

public class PieceDao {

    private final DaoConfiguration daoConfiguration;

    public PieceDao(DaoConfiguration daoConfiguration) {
        this.daoConfiguration = daoConfiguration;
    }

    public Map<Position, Piece> getAllPieces() {
        final var query = "SELECT `column`, `row`, `team`, `type` FROM piece";
        try (final var connection = daoConfiguration.getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            ResultSet result = preparedStatement.executeQuery();
            Map<Position, Piece> pieces = new HashMap<>();
            while (result.next()) {
                String column = result.getString("column");
                String row = result.getString("row");
                String team = result.getString("team");
                String type = result.getString("type");
                Position position = new Position(
                    Column.createFrom(column),
                    Row.createFrom(row));
                createAndAddPiece(type, pieces, position, team);
            }
            return pieces;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createAndAddPiece(String type, Map<Position, Piece> pieces, Position position, String team) {
        pieces.put(position, new Piece(Team.getTeamFromString(team), PieceType.createPieceBy(type)));
    }

    public void addPieces(Map<Position, Piece> pieces) {
        for (Position position : pieces.keySet()) {
            Piece piece = pieces.get(position);
            addPiece(position, piece);
        }
    }

    public void addPiece(final Position position, Piece piece) {
        final var query = "INSERT INTO piece(`column`, `row`, team, type) VALUES(?, ?, ?, ?)";
        try (final var connection = daoConfiguration.getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position.getColumn().name());
            preparedStatement.setString(2, position.getRow().name());
            preparedStatement.setString(3, piece.getTeam().name());
            preparedStatement.setString(4, piece.getType());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findPieceByPosition(Position position) {
        final var query = "SELECT piece_id FROM piece WHERE `column` = ? AND `row` = ?";
        try (final var connection = daoConfiguration.getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position.getColumn().name());
            preparedStatement.setString(2, position.getRow().name());
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt("piece_id");
            } else {
                return 0;
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePiece(Position position) {
        final var query = "DELETE FROM piece WHERE piece_id = ?";
        try (final var connection = daoConfiguration.getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            int pieceIdFromPosition = findPieceByPosition(position);
            preparedStatement.setInt(1, pieceIdFromPosition);
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePiece(Position departure, Position arrival) {
        final var query = "UPDATE piece SET `column` = ?, `row` = ? WHERE piece_id = ?";
        try (final var connection = daoConfiguration.getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            int pieceIdFromPosition = findPieceByPosition(departure);
            preparedStatement.setString(1, arrival.getColumn().name());
            preparedStatement.setString(2, arrival.getRow().name());
            preparedStatement.setInt(3, pieceIdFromPosition);
            preparedStatement.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePieces() {
        final var query = "TRUNCATE TABLE piece;";
        try (final var connection = daoConfiguration.getConnection();
            final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

