package entity;

import dao.PlayerDao;
import dto.SwitchPlayerTurnRequestDto;
import java.util.List;

public class PlayerRepository {
    private final PlayerDao playerDao;

    public PlayerRepository(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<PlayerEntity> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    public void updateTurn(final List<SwitchPlayerTurnRequestDto> switchPlayerTurnRequestDtos) {
        playerDao.saveSwitchedTurn(switchPlayerTurnRequestDtos);
    }
}
