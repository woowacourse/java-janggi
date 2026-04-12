package janggi.persistence.dao;

import janggi.persistence.entity.PieceEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakePieceDao implements PieceDao{
    private final Map<String, List<PieceEntity>> store = new HashMap<>();

    @Override
    public void createAll(Connection conn, List<PieceEntity> entities) {
        if (entities.isEmpty()) {
            return;
        }
        String gameId = entities.getFirst().gameId();
        store.computeIfAbsent(gameId, k -> new ArrayList<>()).addAll(entities);
    }

    @Override
    public List<PieceEntity> findByGameId(String gameId) {
        return store.getOrDefault(gameId, List.of());
    }

    @Override
    public void deleteByGameId(Connection conn, String gameId) {
        store.remove(gameId);
    }

    @Override
    public void updateAll(Connection conn, String gameId, List<PieceEntity> entities) {
        store.put(gameId, new ArrayList<>(entities));
    }
}
