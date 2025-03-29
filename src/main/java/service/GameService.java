package service;

import dao.GameDao;
import domain.game.JanggiGame;
import domain.piece.Team;
import dto.TurnDto;
import java.util.Optional;

public class GameService {
    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void insertInitializeGameTurn() {
        gameDao.insertGameTurn(Team.CHO);
    }

    public void saveTurn(JanggiGame janggiGame) {
        gameDao.saveTurn(janggiGame.getTurn());
    }

    public Optional<TurnDto> findTurn() {
        return gameDao.findTurnByGameId(1);
    }
}
