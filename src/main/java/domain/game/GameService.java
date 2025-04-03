package domain.game;

import domain.player.Players;

public class GameService {

    private final GameDao gameDao;

    public GameService() {
        this.gameDao = new GameDao();
    }

    public boolean checkIfGameExists(int gameId) {
        return gameDao.checkIfGameExists(gameId);
    }

    public void createGame(int gameId) {
        gameDao.insertGame(gameId);
    }

    public void updateGameInfo(Status status, int gameId, Players players, int thisTurnSequence) {
        int bluePlayerId = players.getBluePlayerId();
        int redPlayerId = players.getRedPlayerId();
        gameDao.updateGame(status.name(), gameId, bluePlayerId, redPlayerId, thisTurnSequence);
    }

    public void updateGameSequence(int gameId, int thisTurnSequence) {
        gameDao.updateGameSequence(gameId, thisTurnSequence);
    }

    public Games getGameById(int gameId) {
        return gameDao.selectGameById(gameId);
    }
}
