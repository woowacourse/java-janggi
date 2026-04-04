package repository.dao;

import java.sql.Connection;
import java.sql.SQLException;
import repository.entity.GameContext;

public interface GameContextDao {
    Long save(Connection connection, GameContext entity) throws SQLException;

    GameContext find(Connection connection, Long entityId) throws SQLException;

    void update(Connection connection, Long entityId, GameContext newEntity) throws SQLException;
}
