package domain.player;

import database.DbConnection;
import domain.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        this.playerDao = new PlayerDao(connection);
    }

    public Players savePlayer(List<String> playerNames, int gameId) throws SQLException {
        Player bluePlayer = playerDao.insertPlayer(playerNames.getFirst(), gameId, Team.BLUE);
        Player redPlayer = playerDao.insertPlayer(playerNames.getLast(), gameId, Team.RED);
        return new Players(List.of(bluePlayer, redPlayer));
    }

    public Players getPlayersByGameId(int gameId) {
        return playerDao.selectPlayersByGameId(gameId);
    }
}
