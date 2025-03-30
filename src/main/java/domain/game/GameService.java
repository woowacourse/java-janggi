package domain.game;

public class GameService {

    private static final String START_GAME = "STARTED";

    private final GameDao gameDao;


    public GameService() {
        this.gameDao = new GameDao();
    }

    public Games createGame(int gameId) {
        return gameDao.insertGame(gameId, START_GAME);
    }
}
