package janggi.repository.game;

import janggi.entity.TurnEntity;
import java.util.Optional;

public interface GameRepository {

    Long save(TurnEntity turn);

    Optional<TurnEntity> findByCurrentTurnById(Long gameId);

    void updateTurn(Long gameId, TurnEntity turn);

}
