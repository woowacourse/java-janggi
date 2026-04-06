package janggi.db.dao;

import janggi.db.entity.GameEntity;

import java.util.Optional;

public interface GameDao {
    Long save(String turn);

    Optional<GameEntity> findLatest();

    void updateTurn(Long gameId, String turn);

    void deleteById(Long gameId);
}
