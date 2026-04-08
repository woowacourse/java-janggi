package janggi.service;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
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
        return gameRepository.findAllActiveGames(connection);
    }

    public ActiveGameSession loadGameSession(Connection connection, long gameId) throws SQLException {
        Board board = boardRepository.findAllByGameId(connection, gameId);
        GameManager gameManager = gameRepository.findByGameId(connection, gameId, board);
        return new ActiveGameSession(gameId, gameManager);
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
        long newGameId = gameRepository.insertGame(connection, newGameManager);
        boardRepository.insertBoard(connection, newGameId, board);
        return new ActiveGameSession(newGameId, newGameManager);
    }

    public void saveGameState(Connection connection, long gameId, GameManager gameManager) throws SQLException {
        connection.setAutoCommit(false);
        try {
            updateAndCommitGameState(connection, gameId, gameManager);
        } catch (SQLException exception) {
            rollbackAndThrow(connection, exception);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private void updateAndCommitGameState(Connection connection, long gameId, GameManager gameManager)
            throws SQLException {
        gameRepository.updateTurn(connection, gameId, gameManager);
        boardRepository.updateBoard(connection, gameId, gameManager.getBoard());
        connection.commit();
    }

    private <T> T rollbackAndThrow(Connection connection, SQLException exception) throws SQLException {
        connection.rollback();
        throw exception;
    }

    public void saveFinished(Connection connection, long gameId, boolean finished) throws SQLException {
        gameRepository.updateIsFinished(connection, gameId, finished);
    }
}
