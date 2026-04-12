package application;

import domain.board.SangSetup;
import domain.game.JanggiGame;
import java.util.Optional;
import repository.GameRepository;

public class GamePersistenceService {

    private final GameRepository gameRepository;

    public GamePersistenceService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Optional<JanggiGame> loadLatestRunningGame() {
        return gameRepository.findLatestRunningGame();
    }

    public JanggiGame createNewGame(SangSetup choSangSetup, SangSetup hanSangSetup) {
        JanggiGame game = JanggiGame.of(choSangSetup, hanSangSetup);
        return gameRepository.save(game);
    }

    public void saveProgress(JanggiGame janggiGame) {
        gameRepository.update(janggiGame);
    }
}
