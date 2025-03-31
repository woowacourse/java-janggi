package domain.game;

import domain.player.Players;

public class GameService {

    private static final String START_GAME = "STARTED";

    private final GameDao gameDao;


    public GameService() {
        this.gameDao = new GameDao();
    }

    public Games createGame(int gameId) {
        return gameDao.insertGame(gameId);
    }

    public void updateGameInfo(Status status, int gameId, Players players, int thisTurnSequence) {
        int bluePlayerId = players.getBluePlayer().getId();
        int redPlayerId = players.getRedPlayer().getId();
        gameDao.updateGame(status.name(), gameId, bluePlayerId, redPlayerId, thisTurnSequence);
    }

    public void updateGameSequence(int gameId, int thisTurnSequence) {
        gameDao.updateGameSequence(gameId, thisTurnSequence);
    }

    public Games getGameById(int gameId) {
        return gameDao.selectGameById(gameId);
    }
}
