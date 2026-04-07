package service;

import java.util.function.Supplier;
import repository.GameRepository;

public class GamePersistenceService {

    private final GameRepository gameRepository;

    public GamePersistenceService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public LoadedGame loadOrCreate(Supplier<domain.game.JanggiGame> newGameSupplier) {
        return gameRepository.findInProgressGame()
                .orElseGet(() -> createNewGame(newGameSupplier));
    }

    public void save(LoadedGame loadedGame) {
        gameRepository.save(loadedGame);
    }

    private LoadedGame createNewGame(Supplier<domain.game.JanggiGame> newGameSupplier) {
        return gameRepository.save(new LoadedGame(null, newGameSupplier.get()));
    }
}
