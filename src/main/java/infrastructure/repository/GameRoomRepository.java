package infrastructure.repository;

import domain.room.GameRoom;
import java.util.List;

public interface GameRoomRepository {
    List<GameRoom> findAllPlaying();
    GameRoom save(String name);
    void finish(long roomId);
}
