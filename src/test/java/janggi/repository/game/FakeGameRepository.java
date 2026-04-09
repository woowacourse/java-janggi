package janggi.repository.game;

import janggi.domain.game.GameState;
import janggi.entity.GameEntity;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeGameRepository implements GameRepository {

    private long sequence = 1L;
    private final Map<Long, GameEntity> games = new HashMap<>();

    @Override
    public Long save(Connection connection, GameEntity game) {
        long id = sequence++;
        games.put(id, game);
        return id;
    }

    @Override
    public void update(Connection connection, Long gameId, GameEntity game) {
        if (!games.containsKey(gameId)) {
            throw new IllegalArgumentException(String.format("gameId가 %s인 게임이 존재하지 않습니다.", gameId));
        }
        games.put(gameId, game);
    }

    @Override
    public List<Long> findAllByState(GameState state) {
        return games.entrySet().stream()
                .filter(entry -> entry.getValue().gameState().equals(state.name()))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Optional<GameEntity> findById(Long gameId) {
        return Optional.ofNullable(games.get(gameId));
    }

}
