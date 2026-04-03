package janggi.repository;

import janggi.entity.GameStateEntity;
import java.util.Optional;

public interface GameStateRepository {

    GameStateEntity save(GameStateEntity gameStateEntity);

    Optional<GameStateEntity> findById(long id);

    long update(GameStateEntity gameStateEntity);

    boolean deleteById(long id);
}
