package janggi.persistence.dao;

import janggi.persistence.entity.GameEntity;
import janggi.persistence.entity.vo.Status;
import janggi.persistence.entity.vo.Turn;

import java.util.List;
import java.util.Optional;

public interface GameDao {
    void create(GameEntity gameEntity);

    List<String> findAllNames();

    Optional<String> findByName(String name);

    void deleteByName(String name);

    Optional<GameEntity> findById(String id);

    void updateStatus(String gameId, Turn turn, Status status);
}
