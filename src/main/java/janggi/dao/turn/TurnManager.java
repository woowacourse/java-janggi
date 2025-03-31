package janggi.dao.turn;

import janggi.dto.TurnDto;
import janggi.piece.players.Team;
import janggi.piece.players.Turn;

public class TurnManager {

    private final TurnDao turnDao;

    public TurnManager(final TurnDao turnDao) {
        this.turnDao = turnDao;
    }

    public void initialize() {
        turnDao.deleteAll();
        turnDao.insert(new TurnDto(Team.CHO, true));
        turnDao.insert(new TurnDto(Team.HAN, false));
    }

    public Turn findCurrentTurn() {
        return turnDao.selectCurrentTeam();
    }

    public void updateCurrentTurn(final Turn turn) {
        final Team currentTeam = turn.getTeam();
        turnDao.updateTurn(currentTeam, true);
        turnDao.updateTurn(currentTeam.getOppositeTeam(), false);
    }
}
