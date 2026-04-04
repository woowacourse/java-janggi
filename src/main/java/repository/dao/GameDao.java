package repository.dao;

import domain.JanggiGame;
import domain.GameId;
import domain.GameContextId;
import domain.GameContext;
import java.sql.Connection;

public interface GameDao {
    void initTable(Connection connection);

    GameId save(Connection connection, JanggiGame entity);

    JanggiGame find(Connection connection, GameId entityId);

    JanggiGame findByGameContextId(Connection connection, GameContextId gameContextId);

    void update(Connection connection, GameId entityId, GameContext newEntity);
}
