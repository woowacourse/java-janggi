package janggi.repository;

import janggi.entity.GameEntity;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    long save(GameEntity gameEntity);

    boolean existsById(long id);

    List<Long> findAllIdsOrderByLatest(int limit);

    Optional<GameEntity> findById(long id);

    long updateById(long id, GameEntity gameEntity);

    boolean deleteById(long id);
}
