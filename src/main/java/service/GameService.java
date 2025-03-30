package service;

import java.util.List;
import repository.GameRepository;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean hasPlayingGame() {
        return gameRepository.hasPlayingGame();
    }

    public List<String> findGameNameAll() {
        return gameRepository.findGameNameAll();
    }
}
