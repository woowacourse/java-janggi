package repository;

import entity.GameEntity;

public interface GameDao {

    GameEntity save(GameEntity entity);

    void update(Long gameId, String turnName, String status);
}
