package domain.game;

import java.util.Optional;

public interface GameRepository {
    Optional<SavedGame> findInProgress();

    SavedGame save(Game game);

    SavedGame save(SavedGame savedGame);

    void deleteInProgress();
}
