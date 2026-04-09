package janggi.service;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.dto.GameSessionDTO;
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

    public GameManager loadGameSession(Connection connection, long gameId) throws SQLException {
        return gameRepository.findByGameId(connection, gameId);
    }

    private GameManager generateGameManagerByLoadedData(GameSessionDTO gameSession, Board board) {
        long gameId = gameSession.gameId();
        String choPlayerName = gameSession.choPlayerName();
        String hanPlayerName = gameSession.hanPlayerName();
        Turn currentTurn = new Turn(Side.valueOf(gameSession.currentTurn()));
        Players players = Players.fromCurrentTurn(choPlayerName, hanPlayerName, currentTurn);
        return GameManager.loadGame(players, board, gameId);
    }

    public GameManager createNewSession(Connection connection, String choName, String hanName)
            throws SQLException {
        connection.setAutoCommit(false);
        try {
            return createAndCommitNewGame(connection, choName, hanName);
        } catch (SQLException exception) {
            return rollbackAndThrow(connection, exception);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private GameManager createAndCommitNewGame(Connection connection, String choName, String hanName)
            throws SQLException {
        GameManager gameManager = newGame(connection, choName, hanName);
        connection.commit();
        return gameManager;
    }

    private GameManager newGame(Connection connection, String choName, String hanName)
            throws SQLException {
        Players players = Players.from(choName, hanName);
        Board board = Board.initialize();
        GameManager newGameManager = GameManager.newGame(players, board);
        long gameId = gameRepository.save(connection, newGameManager);
        newGameManager.assign(gameId);
        return newGameManager;
    }

    public void saveGameState(Connection connection, GameManager gameManager) throws SQLException {
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
