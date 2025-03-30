package infrastructure.repository;

import application.persistence.TurnRepository;
import domain.game.Turn;
import infrastructure.dao.TurnDao;
import infrastructure.entity.TurnEntity;

public class TurnJdbcRepository implements TurnRepository {

    private final TurnDao turnDao;

    public TurnJdbcRepository(TurnDao turnDao) {
        this.turnDao = turnDao;
    }

    @Override
    public Turn findTurn() {
        TurnEntity turnEntity = turnDao.findTurn();
        return turnEntity.toDomain();
    }

    @Override
    public void updateTurn(Turn current) {
        TurnEntity turnEntity = TurnEntity.from(current);
        turnDao.updateTurn(turnEntity);
    }

    @Override
    public void save(Turn current) {
        TurnEntity turnEntity = TurnEntity.from(current);
        turnDao.save(turnEntity);
    }

    @Override
    public void delete() {
        turnDao.delete();
    }

}
