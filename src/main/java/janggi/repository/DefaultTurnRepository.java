package janggi.repository;

import janggi.database.dao.TurnDao;
import janggi.domain.Team;
import janggi.domain.Turn;
import java.util.Optional;

public class DefaultTurnRepository implements TurnRepository {

    private final TurnDao turnDao;

    public DefaultTurnRepository(final TurnDao turnDao) {
        this.turnDao = turnDao;
    }

    @Override
    public Long add(final Turn turn) {
        return turnDao.add(turn.getTurn().name());
    }

    @Override
    public Optional<Turn> findCurrent() {
        return turnDao.find()
                .map(turnEntity -> new Turn(Team.valueOf(turnEntity.getTeam())));
    }

    @Override
    public void change(final Turn turn) {
        turnDao.update(turn.getTurn().name());
    }

    @Override
    public void deleteCurrent() {
        turnDao.delete();
    }
}
