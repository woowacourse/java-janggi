package repository;

import domain.game.JanggiGame;
import java.util.Optional;

public interface GameRepository {

    JanggiGame save(JanggiGame janggiGame);

    Optional<JanggiGame> findLatestRunningGame();

    void update(JanggiGame janggiGame);
}
