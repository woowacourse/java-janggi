package janggi.service;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.point.Point;
import janggi.entity.GameEntity;
import janggi.entity.MoveEntity;
import janggi.repository.GameRepository;
import janggi.repository.MoveRepository;
import java.util.List;

public class GameService {
    private final GameRepository gameRepository;
    private final MoveRepository moveRepository;

    public GameService(GameRepository gameRepository, MoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.moveRepository = moveRepository;
    }

    public Game createGame(String gameName, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        Integer id = gameRepository.save(GameEntity.of(gameName, choSetUp, hanSetUp));
        return Game.createGameWithId(id, choSetUp, hanSetUp);
    }

    public List<String> findAllGameNames() {
        return gameRepository.findAllNames();
    }

    public Game findByName(String gameName) {
        GameEntity gameEntity = gameRepository.findByName(gameName)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게임 이름이 없습니다. : " + gameName));
        BoardSetUp choBoardSetUp = gameEntity.choSetUp().getBoardSetUp();
        BoardSetUp hanBoardSetUp = gameEntity.hanSetUp().getBoardSetUp();

        Game game = Game.createGameWithId(gameEntity.id(), choBoardSetUp, hanBoardSetUp);
        List<MoveEntity> moveEntities = moveRepository.findByGameIdOrderByMoveNumber(gameEntity.id());
        moveEntities.forEach(moveEntity -> loadMove(game, moveEntity));
        return game;
    }

    private void loadMove(Game game, MoveEntity moveEntity) {
        game.move(new Point(moveEntity.fromX(), moveEntity.fromY()), new Point(moveEntity.toX(), moveEntity.toY()));
    }

    public void updateWinner(Game game) {
        gameRepository.updateWinner(game.getId(), game.winnerSide());
    }
}
