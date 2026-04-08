package db.repository;

import db.connector.Connector;
import db.parser.SideParser;
import db.session.Session;
import db.util.DataAccessException;
import db.util.Transaction;
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
        return transaction.execute(connection -> {
            Session<JanggiGame> gameSession = saveGame(game, connection);
            pieceRepository.save(game.getBoard(), gameSession.id(), connection);

            return gameSession;
        });
    }

    public List<Integer> findAllIds() {
        return transaction.execute(connection -> {
            String findAllIds = "SELECT id FROM game";
            try (
                    PreparedStatement statement = connection.prepareStatement(findAllIds);
                    ResultSet resultSet = statement.executeQuery()
            ) {
                return parseGameIds(resultSet);
            }
        });
    }

    public Session<JanggiGame> findById(int gameId) {
        return transaction.execute(connection -> {
            Side currentTurn = findCurrentTurn(gameId, connection);
            Map<Intersection, Piece> pieces = pieceRepository.findByGameId(gameId, connection);

            Board board = new Board(new AlivePieces(pieces));
            JanggiGame game = new JanggiGame(board, currentTurn);

            return new Session<>(game, gameId);
        });
    }

    public void update(Session<JanggiGame> gameSession) {
        transaction.execute(connection -> {
            updateCurrentTurn(gameSession, connection);
            updatePieces(gameSession, connection);
        });
    }

    public void delete(Session<JanggiGame> gameSession) {
        transaction.execute(connection -> {
            int gameId = gameSession.id();
            pieceRepository.delete(gameId, connection);

            deleteGame(gameId, connection);
        });
    }

    private Session<JanggiGame> saveGame(
            JanggiGame game,
            Connection connection
    ) throws SQLException {
        String save = "INSERT INTO game (current_turn) values (?)";

        try (PreparedStatement statement = connection.prepareStatement(save, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, SideParser.sideToString(game.getCurrentTurn()));
            statement.executeUpdate();

            int gameId = getGeneratedKey(statement);

            return new Session<>(game, gameId);
        }
    }

    private Side findCurrentTurn(int gameId, Connection connection) throws SQLException {
        String findById = "SELECT current_turn FROM game WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(findById)) {
            statement.setInt(1, gameId);

            return executeFindCurrentTurnById(statement);
        }
    }

    private Side executeFindCurrentTurnById(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            validateResultPresent(resultSet);
            String savedSide = resultSet.getString(1);

            return Side.valueOf(savedSide);
        }
    }

    private void updateCurrentTurn(
            Session<JanggiGame> gameSession,
            Connection connection
    ) throws SQLException {
        String update = "UPDATE game SET current_turn = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(update)) {
            JanggiGame game = gameSession.payload();
            int gameId = gameSession.id();

            statement.setString(1, SideParser.sideToString(game.getCurrentTurn()));
            statement.setInt(2, gameId);
            statement.executeUpdate();
        }
    }

    private void updatePieces(
            Session<JanggiGame> gameSession,
            Connection connection
    ) throws SQLException {
        JanggiGame game = gameSession.payload();
        Map<Intersection, Piece> pieces = game.getBoard();

        pieceRepository.update(pieces, gameSession.id(), connection);
    }

    private void deleteGame(
         int gameId,
         Connection connection
    ) throws SQLException {
        String delete = "DELETE FROM game WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(delete)) {
            statement.setInt(1, gameId);
            statement.executeUpdate();
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

    private void validateResultPresent(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new DataAccessException("장기 게임을 조회하지 못했습니다.");
        }
    }
}
