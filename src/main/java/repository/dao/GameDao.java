package repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import repository.entity.Game;
import repository.entity.GameContext;

public interface GameDao {
    Long save(Connection connection, Game entity) throws SQLException;

    Game find(Connection connection, Long entityId) throws SQLException;

    void update(Connection connection, Long entityId, GameContext newEntity) throws SQLException;
}
