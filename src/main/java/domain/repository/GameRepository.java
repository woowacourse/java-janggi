package domain.repository;

import domain.Game;
import domain.entity.GameRoomEntity;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    void save(Game game);
    Optional<Game> load(Long gameId);
    List<GameRoomEntity> findAllRooms();
}
