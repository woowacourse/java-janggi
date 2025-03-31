package infrastructure.repository;

import application.persistence.GameRepository;
import domain.game.Game;
import infrastructure.dao.GameDao;
import infrastructure.entity.GameEntity;
import java.util.List;

public class GameJdbcRepository implements GameRepository {

    private final GameDao gameDao;

    public GameJdbcRepository(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public Game findTurn() {
        GameEntity gameEntity = gameDao.findTurn();
        return gameEntity.toDomain();
    }

    @Override
    public void updateTurn(Game game) {
        GameEntity gameEntity = GameEntity.from(game);
        gameDao.updateTurn(gameEntity);
    }

    @Override
    public void save(Game game) {
        GameEntity gameEntity = GameEntity.from(game);
        gameDao.save(gameEntity);
    }

    @Override
    public void delete() {
        gameDao.delete();
    }

    @Override
    public List<Game> findAll() {
        List<GameEntity> gameEntities = gameDao.findAll();
        return gameEntities.stream()
                .map(GameEntity::toDomain)
                .toList();
    }
}
