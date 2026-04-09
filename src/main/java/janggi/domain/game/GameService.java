package janggi.domain.game;

import janggi.domain.JanggiGame;
import janggi.dto.GameDto;

import java.util.List;

public class GameService {

    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public Long save() {
        return gameDao.save(GameDto.forSave());
    }

    public List<GameDto> findInProgressGames() {
        return gameDao.findInProgressGames();
    }

    public void updateGameStatusFinished(JanggiGame janggiGame) {
        gameDao.updateGameStatus(janggiGame.getId(), GameStatus.FINISHED);
    }
}
