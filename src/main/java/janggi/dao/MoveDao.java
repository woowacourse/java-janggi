package janggi.dao;

import janggi.dao.entity.MoveEntity;
import java.util.List;

public interface MoveDao {
    // Create
    void save(MoveEntity move);

    // Read
    MoveEntity findById(int id);

    List<MoveEntity> findByGameIdOrderByMoveNumber(int gameId);
}
