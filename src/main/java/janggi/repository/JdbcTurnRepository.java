package janggi.repository;

import janggi.database.dao.TurnDao;
import janggi.domain.Team;
import janggi.domain.Turn;

public class JdbcTurnRepository implements TurnRepository {

    private final TurnDao turnDao;

    public JdbcTurnRepository(final TurnDao turnDao) {
        this.turnDao = turnDao;
    }

    @Override
    public Long add(final Turn turn) {
        return turnDao.add(turn.getTurn().name());
    }

    @Override
    public Turn find() {
        return new Turn(Team.valueOf(turnDao.find().getTeam()));
    }

    @Override
    public void update(final Turn turn) {
        turnDao.update(turn.getTurn().name());
    }

    @Override
    public void delete() {
        turnDao.delete();
    }
}
