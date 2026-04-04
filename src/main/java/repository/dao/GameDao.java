package repository.dao;

import java.sql.SQLException;
import repository.entity.Game;
import repository.entity.GameContext;

public interface GameDao {
    Long save(Game entity) throws SQLException;

    Game find(Long entityId) throws SQLException;

    void update(Long entityId, GameContext newEntity) throws SQLException;
}

//public record GameEntity(
//        Long gameEntityId,
//        Long gameContextEntityId,
//        List<Long> piecesEntitiesIds
//) {
//}
