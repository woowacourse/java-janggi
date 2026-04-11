package janggi.service;

import janggi.dao.BoardDao;
import janggi.dao.GameDao;
import janggi.dao.entity.BoardEntity;
import janggi.dao.entity.GameEntity;
import janggi.dao.entity.MoveEntity;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.Piece;
import janggi.domain.point.Point;
import java.util.List;
import java.util.Map;

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
        Map<Point, Piece> board = game.getBoard();
        BoardEntity boardEntity = new BoardEntity(gameId, board.entrySet()
                .stream()
                .map(MoveEntity::from)
                .toList());
        boardDao.save(boardEntity);

        return new Game(gameId, game.getName(), boardEntity.toDomain(), game.getStatus(), game.getTurn(),
                null);
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
        gameDao.updateWinner(game.getId(), game.winnerSide());
    }

    public void move(Game game, Point from, Point to) {
        game.move(from, to);
        boardDao.delete(game.getId(), from.x(), from.y());

        MoveEntity moveEntity = MoveEntity.of(game, to);
        boardDao.save(game.getId(), moveEntity);
        gameDao.update(GameEntity.fromDomain(game));
    }
}
