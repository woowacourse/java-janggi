package janggi.repository;

import janggi.model.Team;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryGameRepository implements GameRepository {
    private final Map<Long, String> names = new HashMap<>();
    private final Map<Long, Team> turns = new HashMap<>();
    private long autoIncrement = 1L;

    @Override
    public Long save(Team currentTurn, String name) {
        Long id = autoIncrement++;
        names.put(id, name);
        turns.put(id, currentTurn);
        return id;
    }

    @Override
    public List<String> findAllNames() {
        return new ArrayList<>(names.values());
    }

    @Override
    public Optional<Long> findIdByName(String name) {
        return names.entrySet().stream()
                .filter(entry -> entry.getValue().equals(name))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public Optional<Team> findCurrentTurn(Long gameId) {
        return Optional.ofNullable(turns.get(gameId));
    }

    @Override
    public void updateCurrentTurn(Long gameId, Team currentTurn) {
        turns.put(gameId, currentTurn);
    }

    @Override
    public void delete(Long gameId) {
        names.remove(gameId);
        turns.remove(gameId);
    }
}
