package repository;

import java.util.Optional;
import service.LoadedGame;

public interface GameRepository {

    Optional<LoadedGame> findInProgressGame();

    LoadedGame save(LoadedGame loadedGame);
}
