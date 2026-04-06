package service;

import domain.Game;
import repository.GameRepository;

import java.util.Optional;

public class JanggiService {

    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public Optional<Game> load(Long gameId) {
        Game game = gameRepository.load(gameId);
        return Optional.ofNullable(game);
    }
}
