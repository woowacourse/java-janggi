package repository.dao;

import java.sql.SQLException;
import repository.entity.GameContext;

public interface GameContextDao {
    Long save(GameContext entity) throws SQLException;

    GameContext find(Long entityId) throws SQLException;

    void update(Long entityId, GameContext newEntity) throws SQLException;
}
