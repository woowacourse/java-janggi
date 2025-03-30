package dao.fake;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase {
    private final Map<Integer, Boolean> games = new HashMap<>();
    private int nextGameId = 1;

    public int createGame() {
        int id = nextGameId++;
        games.put(id, true);
        return id;
    }

    public void deactivateGame(int gameId) {
        games.put(gameId, false);
    }

    public boolean isGameActive(int gameId) {
        return games.getOrDefault(gameId, false);
    }

    public int getLastGameId() {
        return nextGameId - 1;
    }

    public void createPlayer(int gameId) {

    }

    public void rollback() {
        games.clear();
    }

    public Map<Integer, Boolean> getGames() {
        return games;
    }
}
