package service;

import domain.Game;
import repository.GameRepository;

public class JanggiService {

    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public Game load(Long gameId) {
        return gameRepository.load(gameId);
    }
}
