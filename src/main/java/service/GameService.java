package service;

import dao.GameDao;
import domain.game.Turn;

public class GameService {
    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void initializeGame(){
        gameDao.initializeGameTable();
    }

    public void saveTurn(Turn turn){
        gameDao.saveTurn(turn);
    }
}
