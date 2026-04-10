package janggi.dao;

import janggi.dao.entity.GameEntity;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Optional;

public interface GameDao {
    Integer save(GameEntity game);

    // Read
    GameEntity findById(int id);

    Optional<GameEntity> findByName(String name);

    List<String> findAllNames();

    // Delete
    void delete(int id);

    void updateWinner(Integer id, Side side);
}
