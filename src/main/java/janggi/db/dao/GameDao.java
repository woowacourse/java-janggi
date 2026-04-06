package janggi.db.dao;

import janggi.db.entity.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameDao {
    Long save(String turn);

    Optional<GameEntity> findById(Long gameId);

    List<GameEntity> findAll();

    void updateTurn(Long gameId, String turn);

    void deleteById(Long gameId);
}
