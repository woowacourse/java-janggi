package repository;

import common.GameStatus;
import dao.BoardDao;
import dao.GameInfo;
import dao.JanggiGameDao;
import dao.PlayerNames;
import db.TransactionExecutor;
import domain.board.Board;
import domain.piece.BasicPiece;
import domain.player.Team;
import domain.position.Position;

import java.util.List;
import java.util.Map;

public class JanggiGameRepository {
    private final JanggiGameDao janggiGameDao;
    private final BoardDao boardDao;
    private final TransactionExecutor transactionExecutor;

    public JanggiGameRepository(JanggiGameDao janggiGameDao, BoardDao boardDao, TransactionExecutor transactionExecutor) {
        this.janggiGameDao = janggiGameDao;
        this.boardDao = boardDao;
        this.transactionExecutor = transactionExecutor;
    }

    public long createNewGame(String choName, String hanName, Board board, Team currentTurn) {
        return transactionExecutor.execute(connection -> {
            long gameId = janggiGameDao.createGame(connection, choName, hanName);
            boardDao.saveFullBoard(connection, gameId, board);
            janggiGameDao.updateGameState(connection, gameId, currentTurn, GameStatus.PROGRESS);
            return gameId;
        });
    }

    public void saveTurnProgress(long gameId, Position source, Position destination, BasicPiece movingPiece, Team currentTurn) {
        transactionExecutor.executeVoid(connection -> {
            boardDao.updateMove(connection, gameId, source, destination, movingPiece);
            janggiGameDao.updateGameState(connection, gameId, currentTurn, GameStatus.PROGRESS);
        });
    }

    public void saveFinalMove(long gameId, Position source, Position destination, BasicPiece movingPiece, Team winnerTeam, GameStatus status) {
        transactionExecutor.executeVoid(connection -> {
            boardDao.updateMove(connection, gameId, source, destination, movingPiece);
            janggiGameDao.updateGameState(connection, gameId, winnerTeam, status);
        });
    }

    public List<GameInfo> findAllProgressGames() {
        return janggiGameDao.findAllProgressGames();
    }

    public Team getCurrentTurn(long gameId) {
        return janggiGameDao.getCurrentTurn(gameId);
    }

    public PlayerNames getPlayerNames(long gameId) {
        return janggiGameDao.getPlayerNames(gameId);
    }

    public Map<Position, BasicPiece> loadBoard(long gameId) {
        return boardDao.loadBoard(gameId);
    }
}
