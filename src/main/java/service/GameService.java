package service;

import dao.GameDao;
import java.util.List;

public class GameService {

    private final GameDao gameRepository;

    public GameService(final GameDao gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean hasPlayingGame() {
        return gameRepository.hasPlayingGame();
    }

    public List<String> findGameNameAll() {
        return gameRepository.findGameNameAll();
    }
}
