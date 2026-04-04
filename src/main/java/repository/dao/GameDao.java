package repository.dao;

import java.sql.Connection;
import repository.entity.Game;
import repository.entity.GameContext;

public interface GameDao {
    void initTable(Connection connection);

    Long save(Connection connection, Game entity);

    Game find(Connection connection, Long entityId);

    void update(Connection connection, Long entityId, GameContext newEntity);
}
