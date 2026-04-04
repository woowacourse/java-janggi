package janggi.repository;

import janggi.entity.GameEntity;
import java.util.List;
import java.util.Optional;

public interface GameRepository {
    Integer save(GameEntity game);

    // Read
    GameEntity findById(int id);

    Optional<GameEntity> findByName(String name);

    List<String> findAllNames();

    // Delete
    void delete(int id);
}
