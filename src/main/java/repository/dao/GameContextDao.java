package repository.dao;

import java.sql.Connection;
import java.util.List;
import repository.entity.GameContext;

public interface GameContextDao {
    void initTable(Connection connection);

    Long save(Connection connection, GameContext entity);

    GameContext findById(Connection connection, Long entityId);

    List<GameContext> findByGameState(Connection connection, String gameState);

    void update(Connection connection, Long entityId, GameContext newEntity);
}
