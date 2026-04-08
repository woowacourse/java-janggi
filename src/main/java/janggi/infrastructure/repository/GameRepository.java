package janggi.infrastructure.repository;

import janggi.domain.game.GameStatus;
import janggi.infrastructure.entity.GameEntity;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    long save(GameEntity gameEntity);

    List<Long> findByStatusOrderByLatest(GameStatus gameStatus, int limit);

    Optional<GameEntity> findById(long id);

    long updateById(long id, GameEntity gameEntity);

    long updateStatusById(long id, GameStatus gameStatus);
}
