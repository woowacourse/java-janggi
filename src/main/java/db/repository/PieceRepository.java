package db.repository;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import db.parser.PieceParser;
import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PieceRepository {

    public void save(
            Map<Intersection, Piece> pieces,
            int gameId,
            Connection connection
    ) {
        String save = "INSERT INTO piece (`row`, file, side, type, game_id) VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(
                        save,
                        RETURN_GENERATED_KEYS
                )
        ) {
            batchPieces(statement, pieces, gameId);

            statement.executeBatch();
        } catch (Exception e) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }

    public Map<Intersection, Piece> findByGameId(
            int gameId,
            Connection connection
    ) {
        String findByGameId = "SELECT `row`, file, side, type FROM piece WHERE piece.game_id = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(
                        findByGameId,
                        RETURN_GENERATED_KEYS
                )
        ) {
            statement.setInt(1, gameId);

            ResultSet resultSet = statement.executeQuery();

            return parsePieces(resultSet);
        } catch (Exception e) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }

    public void update(
            Map<Intersection, Piece> pieces,
            int gameId,
            Connection connection
    ) {
        delete(gameId, connection);
        save(pieces, gameId, connection);
    }

    public void delete(
            int gameId,
            Connection connection
    ) {
        String delete = "DELETE FROM piece WHERE game_id = ?";

        try (
                PreparedStatement statement = connection.prepareStatement(delete)
        ) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
        } catch (Exception e) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }

    private void batchPieces(
            PreparedStatement statement,
            Map<Intersection, Piece> pieces,
            int gameId
    ) throws SQLException {
        for (Map.Entry<Intersection, Piece> entry : pieces.entrySet()) {
            Intersection intersection = entry.getKey();
            Piece piece = entry.getValue();

            statement.setInt(1, intersection.getRow());
            statement.setInt(2, intersection.getFile());
            statement.setString(3, piece.getSide().name());
            statement.setString(4, piece.getClass().getSimpleName());
            statement.setInt(5, gameId);

            statement.addBatch();
        }
    }

    private Map<Intersection, Piece> parsePieces(ResultSet resultSet) throws SQLException {
        Map<Intersection, Piece> pieces = new HashMap<>();

        while (resultSet.next()) {
            int row = resultSet.getInt(1);
            int file = resultSet.getInt(2);
            Intersection intersection = new Intersection(row, file);

            Side side = parseSide(resultSet.getString(3));
            String pieceName = resultSet.getString(4);
            Piece piece = PieceParser.from(pieceName, side);

            pieces.put(intersection, piece);
        }

        return pieces;
    }

    private Side parseSide(String sideName) {
        return Side.valueOf(sideName);
    }
}
