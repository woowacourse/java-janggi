package janggi.repository;

import janggi.entity.GameEntity;

public interface GameRepository {
    void save(GameEntity game);

    // Read
    GameEntity findById(int id);

    GameEntity findByName(String name);

    // Delete
    void delete(int id);
}
