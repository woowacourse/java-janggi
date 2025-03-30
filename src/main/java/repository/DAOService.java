package repository;

import domain.board.Point;
import domain.player.Player;
import domain.player.Team;
import java.util.List;
import vo.BoardLocations;
import vo.Choice;

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

    public boolean existsGame(final Choice choice) {
        return gameDAO.existsActiveGameById(choice.value());
    }

    public void deactivateGame(int id) {
        gameDAO.deactivate(id);
    }

    public Player createPlayer(final Team team, final int gameId) {
        final int id = playerDAO.createWithGameId(team, gameId);
        return playerDAO.findById(id);
    }

    public void switchTurn(List<Player> players) {
        playerDAO.updateBatch(players);
    }


    public List<Player> findPlayersByGameId(final Choice choice) {
        return playerDAO.findAllByGameId(choice.value());
    }

    public void registerLocations(BoardLocations boardLocations) {
        boardLocationDAO.createBatch(boardLocations);
    }

    public void changeLocation(Point start, Point arrival, int gameId) {
        boardLocationDAO.deleteLocationAt(arrival, gameId);
        boardLocationDAO.updateLocation(start, arrival, gameId);
    }

    public BoardLocations findLocationByGameId(final Choice choice) {
        return boardLocationDAO.findAllByGameId(choice.value());
    }
}
