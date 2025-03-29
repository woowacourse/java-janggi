package service;

import dao.GameDao;
import domain.game.Turn;
import domain.piece.Team;
import dto.TurnDto;
import java.util.Optional;

public class GameService {
    private final GameDao gameDao;

    public GameService(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void createGame(){
        gameDao.createGameTableIfNotExists();
    }

    public void insertInitializeGameTurn(){
        gameDao.insertGameTurn(Team.CHO);
    }

    public void saveTurn(Turn turn){
        gameDao.saveTurn(turn);
    }

    public Optional<TurnDto> findTurn(){
        return gameDao.findTurnByGameId(1);
    }
}
