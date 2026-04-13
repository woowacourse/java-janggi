package janggi.domain.game;

import janggi.domain.JanggiGame;
import janggi.dto.GameDto;

import java.util.List;
import java.util.Optional;

public class GameRepository {

    private final GameDao gameDao;

    public GameRepository(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void save(JanggiGame janggiGame) {
        gameDao.save(GameDto.from(janggiGame));
    }

    public List<JanggiGame> findInProgressGames() {
        return gameDao.findInProgressGames().stream()
                .map(JanggiGame::from)
                .toList();
    }

    public JanggiGame findById(Long id) {
        Optional<GameDto> gameDto = gameDao.findById(id);
        if (gameDto.isEmpty()) {
            throw new IllegalArgumentException("입력한 ID의 게임 데이터가 존재하지 않습니다.");
        }
        return JanggiGame.from(gameDto.get());
    }

    public void updateGameStatus(JanggiGame janggiGame) {
        gameDao.updateGameStatus(janggiGame.getId(), janggiGame.getGameStatus());
    }
}
