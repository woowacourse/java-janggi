package janggi.persistence.repository;

import janggi.domain.board.Board;
import janggi.domain.game.GameManager;
import janggi.domain.game.Players;
import janggi.domain.game.Side;
import janggi.domain.game.Turn;
import janggi.dto.GameSessionDto;
import janggi.persistence.dao.BoardDao;
import janggi.persistence.dao.GameDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JdbcGameRepository implements GameRepository {

    private final GameDao gameDao;
    private final BoardDao boardDao;

    public JdbcGameRepository(GameDao gameDao, BoardDao boardDao) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
    }

    @Override
    public List<GameSessionDto> findAllGameStatusByFinishedFalse(Connection connection) throws SQLException {
        return gameDao.findAllActive(connection);
    }

    @Override
    public GameManager save(Connection connection, GameManager gameManager) throws SQLException {
        long gameId = processGameSave(connection, gameManager);
        boardDao.deleteByGameId(connection, gameId);
        boardDao.insertAll(connection, gameId, gameManager.getBoard());
        return findByGameId(connection, gameId);
    }

    private long processGameSave(Connection connection, GameManager gameManager) throws SQLException {
        if (gameManager.getId() == null) {
            return gameDao.insert(connection, gameManager);
        }
        gameDao.update(connection, gameManager);
        return gameManager.getId();
    }

    @Override
    public GameManager findByGameId(Connection connection, long gameId) throws SQLException {
        GameSessionDto sessionDto = gameDao.findById(connection, gameId);
        Board board = boardDao.findByGameId(connection, gameId);
        return assembleGameManager(sessionDto, board);
    }

    private GameManager assembleGameManager(GameSessionDto dto, Board board) {
        long gameId = dto.gameId();
        Turn currentTurn = new Turn(Side.valueOf(dto.currentTurn()));
        Players players = Players.fromCurrentTurn(dto.choPlayerName(), dto.hanPlayerName(), currentTurn);
        return GameManager.loadGame(players, board, gameId);
    }
}
