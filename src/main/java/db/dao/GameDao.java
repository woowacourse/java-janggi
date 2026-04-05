package db.dao;

import db.model.GameEntity;
import java.util.Optional;

public interface GameDao {

    Long save(GameEntity gameEntity);

    void update(GameEntity gameEntity);

    Optional<GameEntity> findLatest();
}
