package repository;

import domain.board.Point;
import domain.pieces.Piece;
import domain.player.Player;
import domain.player.Team;
import java.util.List;
import java.util.Map;
import vo.Location;

public final class DAOService {

    private final GameDAO gameDAO;
    private final LocationDAO locationDAO;
    private final PlayerDAO playerDAO;

    public DAOService(final GameDAO gameDAO,
                      final LocationDAO locationDAO,
                      final PlayerDAO playerDAO
    ) {
        this.gameDAO = gameDAO;
        this.locationDAO = locationDAO;
        this.playerDAO = playerDAO;
    }

    public int createGameRoom() {
        return gameDAO.create();
    }

    public Player createPlayer(final Team team, final int gameId) {
        final int id = playerDAO.createWithGameId(team, gameId);
        return playerDAO.findById(id);
    }

    public void deactivateGame(int id) {
        gameDAO.deactivate(id);
    }

    public void registerLocations(Map<Point, Piece> board) {
        final List<Location> locations = board.entrySet().stream()
                .map(entry -> new Location(entry.getKey(), entry.getValue()))
                .toList();
        locationDAO.createBatch(locations);
    }

    public void changeLocation(Point start, Point arrival, int gameId) {
        locationDAO.deleteLocationAt(start, gameId);
        locationDAO.updateLocation(start, arrival, gameId);
    }

    public void switchTurn(List<Player> players) {
        playerDAO.updateBatch(players);
    }
}
