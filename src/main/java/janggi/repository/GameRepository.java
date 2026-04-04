package janggi.repository;

import janggi.domain.side.Side;
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

    void updateWinner(Integer id, Side side);
}
