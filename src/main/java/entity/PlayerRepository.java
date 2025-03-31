package entity;

import dao.PlayerDao;
import java.util.List;

public class PlayerRepository {
    private final PlayerDao playerDao;

    public PlayerRepository(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<PlayerEntity> getAllPlayers() {
        return playerDao.getAllPlayers();
    }
}
