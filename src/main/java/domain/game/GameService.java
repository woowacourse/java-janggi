package domain.game;

import database.DbConnection;
import domain.exception.DatabaseException;
import domain.player.Players;
import java.sql.Connection;
import java.sql.SQLException;

public class GameService {

    private final GameDao gameDao;

    public GameService() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            this.gameDao = new GameDao(connection);
        } catch (SQLException se) {
            throw new DatabaseException("jdbc 연결 오류");
        }
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
