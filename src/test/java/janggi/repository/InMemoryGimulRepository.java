package janggi.repository;

import janggi.model.gimul.AbstractGimul;
import janggi.model.position.Position;
import java.util.HashMap;
import java.util.Map;

public class InMemoryGimulRepository implements GimulRepository {
    private final Map<Long, Map<Position, AbstractGimul>> boards = new HashMap<>();

    @Override
    public void saveAll(Long gameId, Map<Position, AbstractGimul> board) {
        boards.put(gameId, new HashMap<>(board));
    }

    @Override
    public void deleteAll(Long gameId) {
        boards.remove(gameId);
    }

    @Override
    public Map<Position, AbstractGimul> findAll(Long gameId) {
        return boards.getOrDefault(gameId, new HashMap<>());
    }
}
