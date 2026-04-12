package janggi.persistence.dao;


import janggi.exception.DuplicateGameException;
import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeGameDao implements GameDao {
    private final Map<String, GameEntity> store = new HashMap<>();
    private int findByIdCount = 0;

    @Override
    public void create(Connection conn, GameEntity gameEntity) {
        if (store.values().stream().anyMatch(e -> e.name().equals(gameEntity.name()))) {
            throw new DuplicateGameException("이미 존재하는 게임입니다.", null);
        }
        store.put(gameEntity.id(), gameEntity);
    }

    @Override
    public List<String> findAllNames() {
        return store.values().stream().map(GameEntity::name).toList();
    }

    @Override
    public Optional<String> findByName(String name) {
        return store.values().stream()
                .filter(e -> e.name().equals(name))
                .map(GameEntity::id)
                .findFirst();
    }

    @Override
    public void deleteById(Connection conn, String id) {
        store.remove(id);
    }

    @Override
    public Optional<GameEntity> findById(String id) {
        findByIdCount++;
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public void updateStatus(Connection conn, String gameId, Turn turn, Status status) {
        GameEntity old = store.get(gameId);
        store.put(gameId, new GameEntity(old.id(), old.name(), status, turn));
    }

    public int getFindByIdCount() {
        return findByIdCount;
    }
}