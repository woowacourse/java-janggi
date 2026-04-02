package janggi.repository;

import janggi.entity.GameStateEntity;

public interface GameStateRepository {

    long save(GameStateEntity gameStateEntity);

    GameStateEntity findById(long id);

    long update(GameStateEntity gameStateEntity);

    long deleteById(long id);
}
