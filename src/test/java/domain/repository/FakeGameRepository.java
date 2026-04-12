package domain.repository;

import domain.Game;
import domain.entity.GameRoomEntity;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FakeGameRepository implements GameRepository {

    private final Map<Long, Game> games = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public void save(Game game) {
        if (game.getId() == null) {
            Long newId = idGenerator.getAndIncrement();
            game.assignId(newId);
        }
        games.put(game.getId(), game);
    }

    @Override
    public Optional<Game> load(Long gameId) {
        return Optional.ofNullable(games.get(gameId));
    }

    @Override
    public List<GameRoomEntity> findAllRooms() {
        List<GameRoomEntity> rooms = new ArrayList<>();
        for (Game game : games.values()) {
            rooms.add(new GameRoomEntity(
                    game.getId(),
                    game.isFinished(),
                    LocalDateTime.now()
            ));
        }
        return rooms;
    }
}
