package janggi.repository.game;

import janggi.domain.game.GameState;
import janggi.entity.TurnEntity;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    Long save(Connection connection, TurnEntity turn);

    void updateTurn(Connection connection, Long gameId, TurnEntity turn);

    void updateState(Connection connection, Long gameId, GameState state);

    Optional<TurnEntity> findByCurrentTurnById(Long gameId);

    List<Long> findAllByState(GameState state);

    Optional<GameState> findGameStateById(Long gameId);
    
}
