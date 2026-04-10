package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.GameDao;
import janggi.dao.entity.BoardEntity;
import janggi.dao.entity.GameEntity;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.point.Point;
import java.util.List;

public class GameService {
    private final GameDao gameDao;
    private final BoardDao boardDao;

    public GameService(GameDao gameDao, BoardDao boardDao) {
        this.gameDao = gameDao;
        this.boardDao = boardDao;
    }

    public Game createGame(String name, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        Game game = Game.createGame(name, choSetUp, hanSetUp);
        int gameId = gameDao.save(GameEntity.fromDomain(game));
        boardDao.save(new BoardEntity(gameId, game.getBoard()));

        BoardEntity boardEntity = boardDao.getByGameId(gameId);
        return new Game(gameId, game.getName(), boardEntity.toDomain(), game.getStatus(), game.getTurn(), null);
    }

    public List<String> findAllGameNames() {
        return gameDao.findAllNames();
    }

    public Game findByName(String gameName) {
        GameEntity gameEntity = gameDao.findByName(gameName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게임 이름이 없습니다. : " + gameName));

        return gameEntity.toDomain(boardDao.getByGameId(gameEntity.id()).toDomain());
    }

    public void updateWinner(Game game) {
        gameDao.updateWinner(game.getId(), game.winnerSide());
    }

    public void move(Game game, Point from, Point to) {
        game.move(from, to);
        boardDao.save(new BoardEntity(game.getId(), game.getBoard()));
        gameDao.update(GameEntity.fromDomain(game));
    }
}
