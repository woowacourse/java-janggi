package domain.player;

import domain.Team;
import java.util.List;

public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService() {
        this.playerDao = new PlayerDao();
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
