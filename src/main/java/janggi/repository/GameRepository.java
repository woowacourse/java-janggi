package janggi.repository;

import janggi.domain.game.CurrentTurn;
import janggi.entity.GameEntity;
import java.util.Optional;

public interface GameRepository {

    Long save(GameEntity game);

    Optional<CurrentTurn> findByCurrentTurnById(Long gameId);

    void updateTurn(Long gameId, GameEntity game);

}
