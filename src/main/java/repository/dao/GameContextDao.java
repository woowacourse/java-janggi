package repository.dao;

import java.sql.Connection;
import repository.entity.GameContext;

public interface GameContextDao {
    void initTable(Connection connection);

    Long save(Connection connection, GameContext entity);

    GameContext find(Connection connection, Long entityId);

    void update(Connection connection, Long entityId, GameContext newEntity);
}
