package repository;

import entity.GameRoomEntity;
import java.sql.Connection;
import java.util.List;

public interface GameRoomRepository {
    long save(String name, Connection conn);

    List<GameRoomEntity> findAll();

    boolean existsById(long id);
}
