package infra.repository;

import domain.piece.Team;
import domain.turn.Turn;
import domain.turn.repository.TurnRepository;
import infra.dao.TurnDao;
import infra.entity.TurnEntity;
import java.util.Optional;

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
    public Optional<Turn> findLast() {
        final TurnEntity turnEntity = turnDao.findLast();
        if (turnEntity == null) {
            return Optional.empty();
        }

        final Team team = Team.valueOf(turnEntity.getTeam());

        return Optional.of(new Turn(team));
    }

    @Override
    public void deleteAll() {
        turnDao.deleteAll();
    }
}
