package repository;

import domain.board.Point;
import domain.player.Player;
import domain.player.Team;
import java.util.List;
import vo.BoardLocation;

public final class DAOService {

    private final GameDAO gameDAO;
    private final BoardLocationDAO boardLocationDAO;
    private final PlayerDAO playerDAO;

    public DAOService(final GameDAO gameDAO,
                      final BoardLocationDAO boardLocationDAO,
                      final PlayerDAO playerDAO
    ) {
        this.gameDAO = gameDAO;
        this.boardLocationDAO = boardLocationDAO;
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

    public void registerLocations(List<BoardLocation> boardLocations) {
        boardLocationDAO.createBatch(boardLocations);
    }

    public void changeLocation(Point start, Point arrival, int gameId) {
        boardLocationDAO.deleteLocationAt(start, gameId);
        boardLocationDAO.updateLocation(start, arrival, gameId);
    }

    public void switchTurn(List<Player> players) {
        playerDAO.updateBatch(players);
    }
}
