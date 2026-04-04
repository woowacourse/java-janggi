package repository.dao;

import domain.JanggiGame;
import domain.GameId;
import domain.GameContextId;
import domain.GameContext;
import java.sql.Connection;
import java.util.List;

public interface GameDao {
    void initTable(Connection connection);

    GameId save(Connection connection, JanggiGame entity);

    JanggiGame find(Connection connection, GameId entityId);

    JanggiGame findByGameContextId(Connection connection, GameContextId gameContextId);

    List<GameId> findPlayingGameIds(Connection connection);

    void update(Connection connection, GameId entityId, GameContext newEntity);
}
