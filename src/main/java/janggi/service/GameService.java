package janggi.service;

import janggi.dao.GameDao;
import janggi.dao.MoveDao;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.MoveEntity;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.game.Status;
import janggi.domain.point.Point;
import java.util.List;

public class GameService {
    private final GameDao gameDao;
    private final MoveDao moveDao;

    public GameService(GameDao gameDao, MoveDao moveDao) {
        this.gameDao = gameDao;
        this.moveDao = moveDao;
    }

    public Game createGame(String gameName, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        Integer id = gameDao.save(GameEntity.of(gameName, choSetUp, hanSetUp));
        return Game.createGameWithId(id, choSetUp, hanSetUp, Status.IN_PROGRESS);
    }

    public List<String> findAllGameNames() {
        return gameDao.findAllNames();
    }

    public Game findByName(String gameName) {
        GameEntity gameEntity = gameDao.findByName(gameName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게임 이름이 없습니다. : " + gameName));
        BoardSetUp choBoardSetUp = gameEntity.choSetUp().getBoardSetUp();
        BoardSetUp hanBoardSetUp = gameEntity.hanSetUp().getBoardSetUp();

        Game game = Game.createGameWithId(gameEntity.id(), choBoardSetUp, hanBoardSetUp, gameEntity.status());
        List<MoveEntity> moveEntities = moveDao.findByGameIdOrderByMoveNumber(gameEntity.id());
        moveEntities.forEach(moveEntity -> loadMove(game, moveEntity));
        return game;
    }

    private void loadMove(Game game, MoveEntity moveEntity) {
        game.move(new Point(moveEntity.fromX(), moveEntity.fromY()), new Point(moveEntity.toX(), moveEntity.toY()));
    }

    public void updateWinner(Game game) {
        gameDao.updateWinner(game.getId(), game.winnerSide());
    }
}
