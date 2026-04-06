package janggi.repository;

import janggi.entity.MoveEntity;
import java.util.List;

public interface MoveRepository {
    // Create
    void save(MoveEntity move);

    // Read
    MoveEntity findById(int id);

    List<MoveEntity> findByGameIdOrderByMoveNumber(int gameId);

    int findNextMoveNumber(int gameId);
}
