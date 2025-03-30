package domain.player;

import domain.Team;
import java.sql.SQLException;
import java.util.List;

public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService() {
        this.playerDao = new PlayerDao();
    }

    public Players savePlayer(List<String> playerNames, int gameId) throws SQLException {
        Player bluePlayer = playerDao.insertPlayer(playerNames.getFirst(), gameId, Team.BLUE);
        Player redPlayer = playerDao.insertPlayer(playerNames.getLast(), gameId, Team.RED);
        return new Players(List.of(bluePlayer, redPlayer));
    }
}
