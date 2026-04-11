package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.GameDao;
import janggi.dao.TransactionManager;
import janggi.dao.entity.BoardEntity;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.MoveEntity;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.point.Point;
import java.util.List;

public class GameService {
    private final GameDao gameDao;
    private final BoardDao boardDao;
    private final TransactionManager transactionManager;

    public GameService(GameDao gameDao, BoardDao boardDao, TransactionManager transactionManager) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
        this.transactionManager = transactionManager;
    }

    public Game createGame(String name, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        return transactionManager.execute(() -> {
            Game game = Game.createGame(name, choSetUp, hanSetUp);
            GameEntity gameEntity = gameDao.save(GameEntity.fromDomain(game));
            BoardEntity boardEntity = BoardEntity.of(gameEntity.id(), game.getBoard());
            boardDao.save(boardEntity);

            return gameEntity.toDomain(boardEntity);
        });
    }

    public List<String> findAllGameNames() {
        return gameDao.findAllNames();
    }

    public Game findByName(String gameName) {
        GameEntity gameEntity = gameDao.findByName(gameName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게임 이름이 없습니다. : " + gameName));

        List<MoveEntity> moveEntities = boardDao.findAllByGameId(gameEntity.id());
        BoardEntity boardEntity = new BoardEntity(gameEntity.id(), moveEntities);
        return gameEntity.toDomain(boardEntity);
    }

    public void updateWinner(Game game) {
        transactionManager.execute(() -> gameDao.updateWinner(game.getId(), game.winnerSide()));
    }

    public void move(Game game, Point from, Point to) {
        transactionManager.execute(() -> {
            game.move(from, to);
            boardDao.delete(game.getId(), from.x(), from.y());

            MoveEntity moveEntity = MoveEntity.of(game, to);
            boardDao.save(game.getId(), moveEntity);
            gameDao.update(GameEntity.fromDomain(game));
        });
    }
}
