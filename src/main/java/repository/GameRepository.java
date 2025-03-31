package repository;

import dao.GameDao;
import model.Game;

public class GameRepository {

    private final GameDao gameDao = new GameDao();

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
}
