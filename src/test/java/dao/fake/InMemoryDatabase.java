package dao.fake;

import domain.board.Point;
import domain.pieces.PieceDefinition;
import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import dto.BoardLocation;
import dto.BoardLocations;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public final class InMemoryDatabase {
    private final Map<Integer, Boolean> games = new HashMap<>();

    private final Map<Integer, Player> players = new HashMap<>();
    private final Map<Player, Integer> playerGameId = new HashMap<>();

    private final Map<Integer, BoardLocation> locations = new HashMap<>();

    public Map<Integer, BoardLocation> getLocations() {
        return new HashMap<>(locations);
    }

    public Map<Integer, Boolean> getGames() {
        return new HashMap<>(games);
    }

    public Map<Integer, Player> getPlayers() {
        return new HashMap<>(players);
    }

    public void createGame() {
        final int id = games.size() + 1;
        games.put(id, true);
    }

    public void deactivateGame(final int gameId) {
        games.put(gameId, false);
    }

    public List<Integer> findAllActivateGames() {
        return games.entrySet().stream()
                .filter(Entry::getValue)
                .map(Entry::getKey)
                .toList();
    }

    public int getLastPlayerId() {
        return players.size();
    }

    public int createPlayer(
            final String team,
            final Double score,
            final boolean isTurn,
            final int gameId
    ) {
        final int id = players.size() + 1;
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

    public List<Player> findAllPlayersByGameId(final int gameId) {
        return playerGameId.entrySet().stream()
                .filter(entry -> entry.getValue().equals(gameId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public int createLocation(final String piece, final int row, final int column, final int playerId) {
        int id = locations.size() + 1;
        final BoardLocation location = new BoardLocation(
                new Point(row, column),
                PieceDefinition.valueOf(piece),
                playerId
        );
        locations.put(id, location);
        return id;
    }

    public int deleteLocationAt(final int row, final int column, final int gameId) {
        final List<Player> players = findAllPlayersByGameId(gameId);
        int target = 0;
        for (Player player : players) {
            target = getKeyOnLocateByPlayer(row, column, player);
            if (target != 0) {
                locations.remove(target);
                break;
            }
        }
        return target;
    }

    public int updateLocation(int startRow, int startColumn, int arrivalRow, int arrivalColumn, int gameId) {
        final List<Player> players = findAllPlayersByGameId(gameId);
        int target = 0;
        for (Player player : players) {
            target = getKeyOnLocateByPlayer(startRow, startColumn, player);
            if (target != 0) {
                final BoardLocation location = locations.remove(target);
                final Point arrival = new Point(arrivalRow, arrivalColumn);
                final BoardLocation newLocation = new BoardLocation(arrival, location.piece(), location.playerId());
                locations.put(target, newLocation);
                break;
            }
        }
        return target;
    }

    private int getKeyOnLocateByPlayer(int row, int column, Player player) {
        return locations.entrySet().stream()
                .filter(entry -> entry.getValue().getPlayerId() == player.getId())
                .filter(entry -> entry.getValue().getRow() == row)
                .filter(entry -> entry.getValue().getColumn() == column)
                .map(Entry::getKey)
                .findFirst()
                .orElse(0);
    }

    public BoardLocations findAllLocationsByGameId(int gameId) {
        final List<Player> playersInGame = findAllPlayersByGameId(gameId);
        List<BoardLocation> boardLocations = new ArrayList<>();
        for (Player player : playersInGame) {
            boardLocations.addAll(locations.values().stream()
                    .filter(boardLocation -> boardLocation.getPlayerId() == player.getId())
                    .toList());
        }
        return new BoardLocations(boardLocations);
    }
}
