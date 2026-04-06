package janggi.repository;

import janggi.domain.dynasty.Dynasty;
import janggi.domain.game.CurrentTurn;
import janggi.domain.game.Game;
import java.util.Optional;

public interface GameRepository {

    Long save(Game game);

    Optional<CurrentTurn> findByCurrentTurnById(Long gameId);

    void updateTurn(Long gameId, Dynasty currentTurn);

}
