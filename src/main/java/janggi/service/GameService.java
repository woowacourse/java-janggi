package janggi.service;

import janggi.dao.DatabaseManager;
import janggi.dao.GameDao;
import janggi.domain.Turn;

import java.util.List;
import java.util.Map;

public class GameService {

    private final GameDao gameDao;

    public GameService() {
        this.gameDao = new GameDao();
    }

    public void createInitialTablesIfNotExists() {
        DatabaseManager.createInitialTable();
    }

    public int makeNewGame() {
        gameDao.addGame(Turn.CHO);

        Map<Integer, String> games = findAllGames();
        return games.keySet().stream()
                .max(Integer::compareTo)
                .orElseThrow(() -> new IllegalStateException("[ERROR] 게임 생성에 실패했습니다."));
    }

    public Map<Integer,String> findAllGames() {
        return gameDao.findAllGames();
    }

    public void updateGameState(Turn turn) {
        gameDao.updateState(getGameId(), turn);
    }

    public Turn getState() {
        String state = gameDao.findStateById(getGameId());
        return Turn.getStateByName(state);
    }

    public List<Turn> getTurns() {
        Turn turn = getState();
        return turn.getTurnsByState();
    }

    private int getGameId() {
        Map<Integer, String> allGames = findAllGames();
        return allGames.keySet().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 진행 중인 게임을 찾을 수 없습니다."));
    }
}
