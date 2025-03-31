package repository;

import java.util.List;

import dao.GameDao;
import model.Game;

public class GameRepository {

    private final GameDao gameDao = new GameDao();

    public List<Game> findAll() {
        return gameDao.selectAll();
    }

    public Game findById(int id) {
        return gameDao.selectById(id);
    }

    public Game save(Game game) {
        if (game.getId() == null) {
            int id = gameDao.insert(game);
            game.setId(id);
        } else {
            gameDao.update(game);
        }
        return game;
    }

    public void remove(Game game) {
        gameDao.delete(game);
    }
}
