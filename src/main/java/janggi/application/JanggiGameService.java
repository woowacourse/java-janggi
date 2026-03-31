package janggi.application;

import janggi.application.dto.GameSummary;
import java.util.List;

public class JanggiGameService {

    private final GameRepository gameRepository;

    public JanggiGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameSummary> findAllGames() {
        return gameRepository.findAll();
    }
}
