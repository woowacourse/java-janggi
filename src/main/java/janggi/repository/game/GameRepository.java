package janggi.repository.game;

import janggi.domain.game.GameState;
import janggi.entity.TurnEntity;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Long save(TurnEntity turn);

    Optional<TurnEntity> findByCurrentTurnById(Long gameId);

    void updateTurn(Long gameId, TurnEntity turn);

    List<Long> findAllByState(GameState state);

    void updateState(Long gameId, GameState state);

}
