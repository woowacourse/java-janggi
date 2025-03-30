package janggi.dao;

import janggi.domain.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeGameRoomDAO implements GameRoomDAO {

    private final Map<String, Team> database = new HashMap<>();

    @Override
    public List<String> findAllNames() {
        return database.keySet().stream()
                .toList();
    }

    @Override
    public Team findTurn(String gameRoomName) {
        validateExistGameRoom(gameRoomName);
        return database.get(gameRoomName);
    }

    @Override
    public boolean exist(String gameRoomName) {
        return database.containsKey(gameRoomName);
    }

    @Override
    public void create(String gameRoomName) {
        if (database.containsKey(gameRoomName)) {
            throw new IllegalArgumentException();
        }

        database.put(gameRoomName, Team.CHO);
    }

    @Override
    public void update(String gameRoomName, Team team) {
        validateExistGameRoom(gameRoomName);

        database.put(gameRoomName, team);
    }

    @Override
    public void delete(String gameRoomName) {
        database.remove(gameRoomName);
    }

    public void validateExistGameRoom(String gameRoomName) {
        if (!database.containsKey(gameRoomName)) {
            throw new IllegalArgumentException();
        }
    }
}
