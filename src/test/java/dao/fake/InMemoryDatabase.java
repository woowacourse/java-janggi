package dao.fake;

import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class InMemoryDatabase {
    private final Map<Integer, Boolean> games = new HashMap<>();

    private final Map<Integer, Player> players = new HashMap<>();
    private final Map<Player, Integer> playerGameId = new HashMap<>();

    private int nextId = 1;

    public void createGame() {
        int id = nextId++;
        games.put(id, true);
    }

    public void deactivateGame(final int gameId) {
        games.put(gameId, false);
    }

    public boolean isGameActive(final int gameId) {
        return games.getOrDefault(gameId, false);
    }

    public int getLastId() {
        return nextId - 1;
    }

    public Map<Integer, Boolean> getGames() {
        return games;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public int createPlayer(
            final String team,
            final Double score,
            final boolean isTurn,
            final int gameId
    ) {
        int id = nextId++;
        final Player player = new Player(id, Team.valueOf(team), new Score(score), isTurn);
        players.put(id, player);
        playerGameId.put(player, gameId);
        return id;
    }


    public Player findPlayerById(final int id) {
        return players.get(id);
    }

    public int updatePlayer(final Double score, final boolean isTurn, final int id) {
        final Player player = players.get(id);
        final Player updated = new Player(player.getId(), player.getTeam(), new Score(score), isTurn);
        players.put(id, updated);
        return id;
    }

    public List<Player> findAllPlayersByGameId(int gameId) {
        return playerGameId.entrySet().stream()
                .filter(entry -> entry.getValue().equals(gameId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


}
