package db.repository;

import db.connector.Connector;
import db.parser.SideParser;
import db.session.Session;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameRepository {

    private final Transaction transaction;
    private final PieceRepository pieceRepository;

    public GameRepository(
            Connector connector,
            PieceRepository pieceRepository
    ) {
        this.transaction = new Transaction(connector);
        this.pieceRepository = pieceRepository;
    }

    public Session<JanggiGame> save(JanggiGame game) {
        String save = "INSERT INTO game (current_turn) values (?)";

        return transaction.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, SideParser.sideToString(game.getCurrentTurn()));
                statement.executeUpdate();

                int gameId = getGeneratedKey(statement);
                pieceRepository.save(game.getBoard(), gameId, connection);

                return new Session<>(game, gameId);
            }
        });
    }

    public List<Integer> findAllIds() {
        String findAllIds = "SELECT id FROM game";

        return transaction.execute(connection -> {
            try (
                    PreparedStatement statement = connection.prepareStatement(findAllIds);
                    ResultSet resultSet = statement.executeQuery()
            ) {
                return parseGameIds(resultSet);
            }
        });
    }

    public Session<JanggiGame> findById(int gameId) {
        String findById = "SELECT current_turn FROM game WHERE id = ?";

        return transaction.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(findById);) {
                Side currentTurn = findCurrentTurn(statement, gameId);
                Map<Intersection, Piece> pieces = pieceRepository.findByGameId(gameId, connection);

                Board board = new Board(new AlivePieces(pieces));
                JanggiGame game = new JanggiGame(board, currentTurn);

                return new Session<>(game, gameId);
            }
        });
    }

    public void update(Session<JanggiGame> gameSession) {
        String update = "UPDATE game SET current_turn = ? WHERE id = ?";

        transaction.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(update)) {
                JanggiGame game = gameSession.payload();
                int gameId = gameSession.id();
                Map<Intersection, Piece> pieces = game.getBoard();

                pieceRepository.update(pieces, gameId, connection);

                statement.setString(1, SideParser.sideToString(game.getCurrentTurn()));
                statement.setInt(2, gameId);
                statement.executeUpdate();
            }
        });
    }

    public void delete(Session<JanggiGame> gameSession) {
        String delete = "DELETE FROM game WHERE id = ?";

        transaction.execute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(delete)) {
                int gameId = gameSession.id();

                pieceRepository.delete(gameId, connection);

                statement.setInt(1, gameId);
                statement.executeUpdate();
            }
        });
    }

    private Side findCurrentTurn(PreparedStatement findById, int gameId) throws SQLException {
        findById.setInt(1, gameId);

        try (ResultSet resultSet = findById.executeQuery()) {
            String savedSide = resultSet.getString(1);

            return Side.valueOf(savedSide);
        }
    }

    private List<Integer> parseGameIds(ResultSet resultSet) throws SQLException {
        List<Integer> gameIds = new ArrayList<>();

        while (resultSet.next()) {
            gameIds.add(resultSet.getInt(1));
        }

        return gameIds;
    }

    private int getGeneratedKey(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            generatedKeys.next();

            return generatedKeys.getInt(1);
        }
    }
}
