package data;

import java.sql.Connection;
import java.util.Optional;

public interface GameDao {
    GameEntity insert(Connection conn, GameEntity gameEntity);

    GameEntity update(Connection conn, GameEntity gameEntity);

    Optional<GameEntity> findById(Connection conn, Long id);

    void deleteById(Connection conn, Long id);
}
