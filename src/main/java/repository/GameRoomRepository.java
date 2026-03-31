package repository;

import entity.GameRoomEntity;
import java.util.List;

public interface GameRoomRepository {
    long save(String name);

    List<GameRoomEntity> findAll();

    GameRoomEntity findById(long id);
}
