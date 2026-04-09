package janggi.domain;

import janggi.dto.GameDto;

import java.util.List;

public class GameService {

    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public List<GameDto> findInProgressGames() {
        return gameDao.findInProgressGames();
    }
}
