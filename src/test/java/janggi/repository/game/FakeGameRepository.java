package janggi.repository.game;

import janggi.domain.game.GameState;
import janggi.entity.TurnEntity;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeGameRepository implements GameRepository {

    private long sequence = 1L;
    private final Map<Long, TurnEntity> turns = new HashMap<>();
    private final Map<Long, GameState> states = new HashMap<>();

    @Override
    public Long save(Connection connection, TurnEntity turn) {
        long id = sequence++;
        turns.put(id, turn);
        states.put(id, GameState.PLAYING);
        return id;
    }

    @Override
    public void updateTurn(Connection connection, Long gameId, TurnEntity game) {
        turns.put(gameId, game);
    }

    @Override
    public void updateState(Connection connection, Long gameId, GameState state) {
        states.put(gameId, state);
    }

    @Override
    public List<Long> findAllByState(GameState state) {
        return states.entrySet().stream()
                .filter(entry -> entry.getValue() == state)
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Optional<TurnEntity> findByCurrentTurnById(Long gameId) {
        return Optional.ofNullable(turns.get(gameId));
    }

    @Override
    public Optional<GameState> findGameStateById(Long gameId) {
        return Optional.ofNullable(states.get(gameId));
    }

}
