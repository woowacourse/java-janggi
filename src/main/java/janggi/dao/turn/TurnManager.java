package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.domain.players.Team;
import janggi.domain.players.Turn;

public class TurnManager {

    private final TurnDao turnDao;

    public TurnManager(final TurnDao turnDao) {
        this.turnDao = turnDao;
    }

    public void initialize() {
        turnDao.initialize(new TurnDto(Team.CHO));
    }

    public Turn findCurrentTurn() {
        return turnDao.selectCurrentTeam();
    }

    public void updateCurrentTurn(final Turn turn) {
        final Team currentTeam = turn.getTeam();
        turnDao.updateTurn(currentTeam);
    }
}
