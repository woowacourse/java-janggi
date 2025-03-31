package infra.repository;

import domain.piece.Team;
import domain.turn.Turn;
import domain.turn.repository.TurnRepository;
import infra.dao.TurnDao;
import infra.entity.TurnEntity;

public class TurnRepositoryAdapter implements TurnRepository {

    private final TurnDao turnDao;

    public TurnRepositoryAdapter(final TurnDao turnDao) {
        this.turnDao = turnDao;
    }

    @Override
    public void save(final Turn turn) {
        final TurnEntity turnEntity = new TurnEntity(turn.getCurrentTeam()
            .name());
        turnDao.save(turnEntity);
    }

    @Override
    public boolean exists() {
        return turnDao.exists();
    }

    @Override
    public Turn findLast() {
        final TurnEntity turnEntity = turnDao.findLast();
        final Team team = Team.valueOf(turnEntity.getTeam());

        return new Turn(team);
    }

    @Override
    public void deleteAll() {
        turnDao.deleteAll();
    }
}
