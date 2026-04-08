package db.repository;

import db.connector.MySqlConnector;
import db.parser.SideParser;
import db.session.Session;
import db.util.StatementMode;
import db.util.Transaction;
import domain.board.Board;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameRepository {

    private final Transaction transaction = new Transaction(new MySqlConnector());
    private final PieceRepository pieceRepository = new PieceRepository();

    public Session<JanggiGame> save(JanggiGame game) {
        String save = "INSERT INTO game (current_turn) values (?)";

        return transaction.execute(save, StatementMode.RETURN_GENERATED_KEY, (connection, statement) -> {
            statement.setString(1, SideParser.sideToString(game.getCurrentTurn()));
            statement.executeUpdate();

            int gameId = getGeneratedKey(statement);
            pieceRepository.save(game.getBoard(), gameId, connection);

            return new Session<>(game, gameId);
        });
    }

    public List<Integer> findAllIds() {
        String findAllIds = "SELECT id FROM game";

        return transaction.execute(findAllIds, StatementMode.DEFAULT, (connection, statement) -> {
            try (ResultSet resultSet = statement.executeQuery()) {
                return parseGameIds(resultSet);
            }
        });
    }

    public Session<JanggiGame> findById(int gameId) {
        String findById = "SELECT current_turn FROM game WHERE id = ?";

        return transaction.execute(findById, StatementMode.DEFAULT, (connection, statement) -> {
            Map<Intersection, Piece> pieces = pieceRepository.findByGameId(gameId, connection);
            statement.setLong(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                JanggiGame game = parseGame(resultSet, pieces);

                return new Session<>(game, gameId);
            }
        });
    }

    public void update(Session<JanggiGame> gameSession) {
        String update = "UPDATE game SET current_turn = ? WHERE id = ?";

        transaction.execute(update, StatementMode.DEFAULT, (connection, statement) -> {
            JanggiGame game = gameSession.payload();
            int gameId = gameSession.id();
            Map<Intersection, Piece> pieces = game.getBoard();

            pieceRepository.update(pieces, gameId, connection);

            statement.setString(1, SideParser.sideToString(game.getCurrentTurn()));
            statement.setInt(2, gameId);
            statement.executeUpdate();
        });
    }

    public void delete(Session<JanggiGame> gameSession) {
        String delete = "DELETE FROM game WHERE id = ?";

        transaction.execute(delete, StatementMode.DEFAULT, (connection, statement) -> {
            int gameId = gameSession.id();

            pieceRepository.delete(gameId, connection);

            statement.setInt(1, gameId);
            statement.executeUpdate();
        });
    }

    private List<Integer> parseGameIds(ResultSet resultSet) throws SQLException {
        List<Integer> gameIds = new ArrayList<>();

        while (resultSet.next()) {
            gameIds.add(resultSet.getInt(1));
        }

        return gameIds;
    }

    private JanggiGame parseGame(
            ResultSet resultSet,
            Map<Intersection, Piece> pieces
    ) throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("장기 게임을 조회하지 못했습니다.");
        }

        Board board = new Board(new AlivePieces(pieces));
        Side currentTurn = Side.valueOf(resultSet.getString(1));

        return new JanggiGame(board, currentTurn);
    }

    private int getGeneratedKey(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            generatedKeys.next();

            return generatedKeys.getInt(1);
        }
    }
}
