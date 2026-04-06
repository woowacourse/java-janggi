package repository;

import entity.GameEntity;

public interface GameDao {

    GameEntity save(GameEntity entity);
}
