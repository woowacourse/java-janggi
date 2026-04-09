package dao;

import common.GameStatus;
import domain.board.Board;
import domain.piece.BasicPiece;
import domain.player.Team;
import domain.position.Position;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GamePersistence {
    private final GameDao gameDao;
    private final BoardDao boardDao;

    public GamePersistence(GameDao gameDao, BoardDao boardDao) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
    }

    public long createNewGame(String choName, String hanName, Board board, Team currentTurn) {
        return TransactionExecutor.execute(connection -> {
            long gameId = gameDao.createGame(connection, choName, hanName);
            boardDao.saveFullBoard(connection, gameId, board);
            gameDao.updateGameState(connection, gameId, currentTurn, GameStatus.PROGRESS);
            return gameId;
        });
    }

    public void saveTurnProgress(long gameId, Position source, Position destination, BasicPiece movingPiece, Team currentTurn) {
        TransactionExecutor.executeVoid(connection -> {
            boardDao.updateMove(connection, gameId, source, destination, movingPiece);
            gameDao.updateGameState(connection, gameId, currentTurn, GameStatus.PROGRESS);
        });
    }

    public void saveFinalMove(long gameId, Position source, Position destination, BasicPiece movingPiece, Team winnerTeam, GameStatus status) {
        TransactionExecutor.executeVoid(connection -> {
            boardDao.updateMove(connection, gameId, source, destination, movingPiece);
            gameDao.updateGameState(connection, gameId, winnerTeam, status);
        });
    }

    public List<GameInfo> findAllProgressGames() {
        return gameDao.findAllProgressGames();
    }

    public Team getCurrentTurn(long gameId) {
        return gameDao.getCurrentTurn(gameId);
    }

    public PlayerNames getPlayerNames(long gameId) {
        return gameDao.getPlayerNames(gameId);
    }

    public Map<Position, BasicPiece> loadBoard(long gameId) {
        return boardDao.loadBoard(gameId);
    }
}
