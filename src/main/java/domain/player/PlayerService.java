package domain.player;

import database.DbConnection;
import domain.Team;
import domain.exception.DatabaseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            this.playerDao = new PlayerDao(connection);
        } catch (SQLException se) {
            throw new DatabaseException("jdbc 연결 오류");
        }
    }

    public Players savePlayer(List<String> playerNames, int gameId) {
        Player bluePlayer = playerDao.insertPlayer(playerNames.getFirst(), gameId, Team.BLUE);
        Player redPlayer = playerDao.insertPlayer(playerNames.getLast(), gameId, Team.RED);
        return new Players(bluePlayer, redPlayer);
    }

    public Players getPlayersByGameId(int gameId) {
        return playerDao.selectPlayersByGameId(gameId);
    }
}
