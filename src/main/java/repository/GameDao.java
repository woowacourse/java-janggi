package repository;

import entity.GameEntity;

import java.sql.Connection;
import java.util.List;

public interface GameDao {

    GameEntity save(Connection con, GameEntity entity);

    void update(Connection con, Long gameId, String turnName, String status);

    List<GameEntity> findAll();
}
