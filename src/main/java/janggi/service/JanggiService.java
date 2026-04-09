package janggi.service;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.dto.GameSessionDTO;
import janggi.persistence.ActiveGameSession;
import janggi.persistence.BoardRepository;
import janggi.persistence.GameRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {

    private final GameRepository gameRepository;
    private final BoardRepository boardRepository;

    public JanggiService(GameRepository gameRepository, BoardRepository boardRepository) {
        this.gameRepository = gameRepository;
        this.boardRepository = boardRepository;
    }

    public List<GameSessionDTO> activeGames(Connection connection) throws SQLException {
        return gameRepository.findAllGameStatusByFinishedFalse(connection);
    }

    public ActiveGameSession loadGameSession(Connection connection, long gameId) throws SQLException {
        GameManager gameManager = gameRepository.findByGameId(connection, gameId);
        return new ActiveGameSession(gameId, gameManager);
    }

    private GameManager generateGameManagerByLoadedData(GameSessionDTO gameSession, Board board) {
        Players players = Players.from(gameSession.choPlayerName(), gameSession.hanPlayerName());
        Turn currentTurn = new Turn(Side.valueOf(gameSession.currentTurn()));
        return new GameManager(players, board, currentTurn);
    }

    public ActiveGameSession createNewSession(Connection connection, String choName, String hanName)
            throws SQLException {
        connection.setAutoCommit(false);
        try {
            return executeAndCommitSession(connection, choName, hanName);
        } catch (SQLException exception) {
            return rollbackAndThrow(connection, exception);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private ActiveGameSession executeAndCommitSession(Connection connection, String choName, String hanName)
            throws SQLException {
        ActiveGameSession session = generateSession(connection, choName, hanName);
        connection.commit();
        return session;
    }

    private ActiveGameSession generateSession(Connection connection, String choName, String hanName)
            throws SQLException {
        Players players = Players.from(choName, hanName);
        Board board = Board.initialize();
        GameManager newGameManager = new GameManager(players, board, Turn.init());
        long newGameId = gameRepository.save(connection, newGameManager);
        System.out.println("newGameId " + newGameId);
        return new ActiveGameSession(newGameId, newGameManager);
    }

    public void saveGameState(Connection connection, long gameId, GameManager gameManager) throws SQLException {
        connection.setAutoCommit(false);
        try {
            gameRepository.save(connection, gameManager);
            connection.commit();
        } catch (SQLException exception) {
            rollbackAndThrow(connection, exception);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private <T> T rollbackAndThrow(Connection connection, SQLException exception) throws SQLException {
        connection.rollback();
        throw exception;
    }
}
