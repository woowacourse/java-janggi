package repository.dao;

import java.sql.SQLException;
import repository.entity.GameContextEntity;

public interface GameContextDao {
    Long save(GameContextEntity entity) throws SQLException;

    GameContextEntity find(Long entityId) throws SQLException;

    void update(Long entityId, GameContextEntity newEntity) throws SQLException;
}
