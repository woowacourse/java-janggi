package infrastructure.repository;

import application.persistence.GameRepository;
import domain.game.Game;
import infrastructure.dao.GameDao;
import infrastructure.entity.GameEntity;
import java.util.List;

public class GameRepositoryImpl implements GameRepository {

    private final GameDao gameDao;

    public GameRepositoryImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public void updateGame(Game game) {
        GameEntity gameEntity = GameEntity.from(game);
        gameDao.updateGame(gameEntity);
    }

    @Override
    public Game save(Game game) {
        GameEntity gameEntity = GameEntity.from(game);
        return gameDao.save(gameEntity).toDomain();
    }

    @Override
    public void deleteGame(Game game) {
        GameEntity gameEntity = GameEntity.from(game);
        gameDao.deleteGame(gameEntity);
    }

    @Override
    public List<Game> findAll() {
        List<GameEntity> gameEntities = gameDao.findAll();
        return gameEntities.stream()
                .map(GameEntity::toDomain)
                .toList();
    }
}
