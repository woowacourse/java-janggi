package domain.game;

import java.util.Optional;

public interface GameRepository {
    Optional<Game> findInProgress();

    Game save(Game game);

    void deleteInProgress();
}
