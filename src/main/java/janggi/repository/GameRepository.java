package janggi.repository;

import janggi.entity.GameEntity;
import java.util.Optional;

public interface GameRepository {

    GameEntity save(GameEntity gameEntity);

    boolean existsById(long id);

    Optional<GameEntity> findById(long id);

    long updateById(long id, GameEntity gameEntity);

    boolean deleteById(long id);
}
