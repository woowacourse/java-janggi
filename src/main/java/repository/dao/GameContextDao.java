package repository.dao;

import domain.GameContext;
import domain.GameContextId;
import java.sql.Connection;
import java.util.List;

public interface GameContextDao {
    void initTable(Connection connection);

    GameContextId save(Connection connection, GameContext entity);

    GameContext findById(Connection connection, GameContextId entityId);

    List<GameContext> findByGameState(Connection connection, String gameState);

    void update(Connection connection, GameContextId entityId, GameContext newEntity);
}
