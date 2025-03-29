package repository;

import domain.player.Player;
import domain.player.Team;

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
}
