package janggi.service;

import janggi.dao.GameDao;
import janggi.domain.GameState;
import janggi.domain.piece.Side;

import java.util.Map;

public class GameService {

    private final GameDao gameDao;

    public GameService() {
        this.gameDao = new GameDao();
    }

    public int makeNewGame() {
        gameDao.addGame(Side.CHO);

        Map<Integer, String> games = findAllGames();
        return games.keySet().stream()
                .max(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("[ERROR] 게임 생성에 실패했습니다."));
    }

    public Map<Integer,String> findAllGames() {
        return gameDao.findAllGames();
    }

    public void updateGameState(int gameId, GameState gameState) {
        gameDao.updateState(gameId, gameState);
    }

    public GameState getState(int gameId) {
        String state = gameDao.findStateById(gameId);
        return GameState.getStateByName(state);
    }
}
