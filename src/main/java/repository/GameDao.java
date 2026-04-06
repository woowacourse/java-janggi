package repository;

import entity.GameEntity;

import java.util.List;

public interface GameDao {

    GameEntity save(GameEntity entity);

    void update(Long gameId, String turnName, String status);

    List<GameEntity> findAll();
}
