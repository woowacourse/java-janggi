package repository;

import domain.Game;
import domain.board.Board;
import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;

import java.util.Map;

public class GameRepository {
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    public GameRepository(GameDao gameDao, PieceDao pieceDao) {
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public void save(long gameId, Game game) {
        gameDao.updateTurn(gameId, game.getTurn().name());
        pieceDao.saveAll(gameId, game.getPieces());
    }

    public Game findById(long gameId) {
        Team turn = gameDao.findTurn(gameId);
        Map<Position, Piece> pieces = pieceDao.findByGameId(gameId);
        return new Game(new Board(pieces), turn);
    }

    public long create(Game game) {
        long gameId = gameDao.save(game.getTurn().name());
        pieceDao.saveAll(gameId, game.getPieces());
        return gameId;
    }

    public Map<Long, String> findAll() {
        return gameDao.findAll();
    }
}
