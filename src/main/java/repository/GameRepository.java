package repository;

import domain.game.JanggiGame;
import java.util.Optional;

public interface GameRepository {

    Optional<Long> findInProgressGameId();

    Optional<JanggiGame> findById(long gameId);

    long save(JanggiGame janggiGame);

    void save(long gameId, JanggiGame janggiGame);
}
