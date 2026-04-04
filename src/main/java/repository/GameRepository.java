package repository;

import domain.game.Game;
import java.util.Optional;

public interface GameRepository {

    void save(Game game);

    Optional<Game> findInProgressGame();
}
