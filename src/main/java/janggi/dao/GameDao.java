package janggi.dao;

import janggi.dao.entity.GameEntity;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Optional;

public interface GameDao {
    GameEntity save(GameEntity game);

    Optional<GameEntity> findByName(String name);

    List<String> findAllNames();

    void updateWinner(Integer id, Side side);

    void update(GameEntity gameEntity);
}
