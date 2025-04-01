package janggi.repository;

import janggi.database.dao.TurnDao;
import janggi.domain.Team;
import janggi.domain.Turn;
import java.util.Optional;

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
    public Optional<Turn> find() {
        return turnDao.find()
                .map(turnEntity -> new Turn(Team.valueOf(turnEntity.getTeam())));
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
