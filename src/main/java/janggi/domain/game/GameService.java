package janggi.domain.game;

import janggi.domain.JanggiGame;
import janggi.dto.GameDto;

import java.util.List;
import java.util.Optional;

public class GameService {

    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void save(JanggiGame janggiGame) {
        gameDao.save(GameDto.from(janggiGame));
    }

    public List<GameDto> findInProgressGames() {
        return gameDao.findInProgressGames();
    }

    public GameDto findById(Long id) {
        Optional<GameDto> gameDto = gameDao.findById(id);
        if (gameDto.isEmpty()) {
            throw new IllegalArgumentException("입력한 ID의 게임 데이터가 존재하지 않습니다.");
        }
        return gameDto.get();
    }

    public void updateGameStatusFinished(JanggiGame janggiGame) {
        gameDao.updateGameStatus(janggiGame.getId(), GameStatus.IN_PROGRESS);
    }
}
