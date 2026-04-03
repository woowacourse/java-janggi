package janggi.service;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.entity.GameEntity;
import janggi.repository.GameRepository;

public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String gameName, BoardSetUp choSetUp, BoardSetUp hanSetUp) {
        Game game = Game.createGame(choSetUp, hanSetUp);
        gameRepository.save(GameEntity.of(game, choSetUp.toString(), hanSetUp.toString()));
        return game;
    }
}
