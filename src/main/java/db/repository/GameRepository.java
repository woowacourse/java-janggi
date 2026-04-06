package db.repository;

import static java.sql.PreparedStatement.*;

import db.connector.MySqlConnector;
import db.session.Session;
import domain.board.Board;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GameRepository {

    private final MySqlConnector connector = new MySqlConnector();
    private final PieceRepository pieceRepository = new PieceRepository();

    public Session<JanggiGame> save(JanggiGame game) {
        String save = "INSERT INTO game (current_turn) values (?)";

        try (
                Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        save,
                        RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, parseSide(game.getCurrentTurn()));

            statement.executeUpdate();
            int gameId = getGeneratedKey(statement);
            pieceRepository.save(game.getBoard(), gameId, connection);

            return new Session<>(game, gameId);
        } catch (Exception exception) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }

    public Session<JanggiGame> findById(int gameId) {
        String findById = "SELECT current_turn FROM game WHERE id = ?";

        try (
                Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(findById)
        ) {
            Map<Intersection, Piece> pieces = pieceRepository.findByGameId(gameId, connection);
            statement.setLong(1, gameId);

            ResultSet resultSet = statement.executeQuery();
            JanggiGame game = parseGame(resultSet, pieces);

            return new Session<>(game, gameId);
        } catch (Exception exception) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }

    public void update(Session<JanggiGame> gameSession) {
        String update = "UPDATE game SET current_turn = ? WHERE id = ?";

        try (
                Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(update)
        ) {
            JanggiGame game = gameSession.payload();
            int gameId = gameSession.id();
            Map<Intersection, Piece> pieces = game.getBoard();

            pieceRepository.update(pieces, gameId, connection);
            statement.setString(1, parseSide(game.getCurrentTurn()));
            statement.setInt(2, gameId);

            statement.executeUpdate();
        } catch (Exception exception) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }

    public void delete(Session<JanggiGame> gameSession) {
        String delete = "DELETE FROM game WHERE id = ?";

        try (
                Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(delete)
        ) {
            connection.setAutoCommit(false);
            int gameId = gameSession.id();

            pieceRepository.delete(gameId, connection);
            statement.setInt(1, gameId);

            statement.executeUpdate();
        } catch (Exception exception) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }

    private String parseSide(Side side) {
        return side.name();
    }

    private JanggiGame parseGame(
            ResultSet resultSet,
            Map<Intersection, Piece> pieces
    ) throws SQLException {
        resultSet.next();

        Board board = new Board(new AlivePieces(pieces));
        Side currentTurn = Side.valueOf(resultSet.getString(1));

        return new JanggiGame(board, currentTurn);
    }

    private int getGeneratedKey(PreparedStatement statement) {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            generatedKeys.next();

            return generatedKeys.getInt(1);
        } catch (SQLException e) {
            // TODO: 적절한 예외
            throw new IllegalStateException();
        }
    }
}
