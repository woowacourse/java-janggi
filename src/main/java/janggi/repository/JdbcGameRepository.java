package janggi.repository;

import java.util.Map;
import java.util.Optional;

import janggi.domain.board.coordinate.Point;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;

public class JdbcGameRepository implements GameRepository {

    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public JdbcGameRepository() {
        this.gameDao = new GameDao();
        this.pieceDao = new PieceDao();
    }

    @Override
    public Optional<Integer> findActiveGameId() {
        return gameDao.findActiveGameId(TransactionManager.getConnection());
    }

    @Override
    public int save(Game game) {
        int gameId = gameDao.insert(TransactionManager.getConnection(), game.getTurn());
        pieceDao.insertAll(TransactionManager.getConnection(), gameId, game.getBoard());
        return gameId;
    }

    @Override
    public void update(int gameId, Game game) {
        gameDao.updateTurn(TransactionManager.getConnection(), gameId, game.getTurn());
        pieceDao.deleteAll(TransactionManager.getConnection(), gameId);
        pieceDao.insertAll(TransactionManager.getConnection(), gameId, game.getBoard());
    }

    @Override
    public Game load(int gameId) {
        Side turn = gameDao.findTurn(TransactionManager.getConnection(), gameId);
        Map<Point, Piece> board = pieceDao.findAll(TransactionManager.getConnection(), gameId);
        return Game.loadGame(board, turn);
    }

    @Override
    public void finish(int gameId, Side winner) {
        gameDao.finish(TransactionManager.getConnection(), gameId, winner);
    }
}
