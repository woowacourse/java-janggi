package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.GameDao;
import janggi.dao.TransactionManager;
import janggi.dao.entity.BoardEntity;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.MoveEntity;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameService {
    private final GameDao gameDao;
    private final BoardDao boardDao;
    private final TransactionManager transactionManager;

    public GameService(GameDao gameDao, BoardDao boardDao, TransactionManager transactionManager) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
        this.transactionManager = transactionManager;
    }

    public GameEntity createGame(String name, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        return transactionManager.execute(() -> {
            Game game = Game.createGame(name, choSetUp, hanSetUp);
            GameEntity gameEntity = gameDao.save(GameEntity.fromDomain(game));
            BoardEntity boardEntity = BoardEntity.of(gameEntity.id(), game.getBoard());
            boardDao.save(boardEntity);

            return gameEntity;
        });
    }

    public List<String> findAllGameNames() {
        return transactionManager.execute(gameDao::findAllNames);
    }

    public GameEntity findByName(String gameName) {
        return transactionManager.execute(() -> gameDao.findByName(gameName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게임 이름이 없습니다. : " + gameName))
        );
    }

    public void move(int gameId, Point from, Point to) {
        transactionManager.execute(() -> {
            Game game = findById(gameId);

            game.validateMove(from, to);
            boardDao.delete(game.getId(), from.x(), from.y());
            boardDao.save(game.getId(), MoveEntity.of(game, from, to));

            game.move(from, to);
            gameDao.update(GameEntity.fromDomain(game));
        });
    }

    public Side getWinnderSide(int gameId) {
        Game game = findById(gameId);
        return game.getWinner();
    }

    public boolean canPlay(int gameId) {
        return transactionManager.execute(() -> {
            Game game = findById(gameId);
            if (!game.canPlay()) {
                gameDao.update(GameEntity.fromDomain(game));
                return false;
            }
            return true;
        });
    }

    public Map<Point, Piece> getBoardByGameId(int gameId) {
        return transactionManager.execute(() -> {
            List<MoveEntity> moveEntities = boardDao.findAllByGameId(gameId);
            return new BoardEntity(gameId, moveEntities).toDomain().getPieces();
        });
    }

    public Side getTurn(int gameId) {
        Game game = findById(gameId);
        return game.getTurn();
    }
    
    public Set<Point> getDestinations(int gameId, Point from) {
        Game game = findById(gameId);
        return game.destinations(from);
    }

    public boolean isTurnPiece(int gameId, Point from) {
        Game game = findById(gameId);
        return game.isTurnPiece(from);
    }

    private Game findById(int gameId) {
        return transactionManager.execute(() -> {
            GameEntity gameEntity = gameDao.findById(gameId)
                    .orElseThrow(() -> new IllegalArgumentException("해당하는 게임이 없습니다. id: " + gameId));
            List<MoveEntity> moveEntities = boardDao.findAllByGameId(gameId);
            return gameEntity.toDomain(new BoardEntity(gameId, moveEntities));
        });
    }
}
