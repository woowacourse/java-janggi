package service;

import domain.dao.JdbcJanggiGameDao;
import domain.dao.JdbcJanggiPositionDao;
import domain.dao.JdbcMoveHistoryDao;
import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.position.JanggiPosition;
import java.util.ArrayList;
import java.util.List;
import util.ProductionDatabaseConnector;

public class JanggiService {

    private final JdbcJanggiGameDao gameDao;
    private final JdbcJanggiPositionDao positionDao;
    private final JdbcMoveHistoryDao historyDao;

    public JanggiService() {
        ProductionDatabaseConnector connector = new ProductionDatabaseConnector();
        this.gameDao = new JdbcJanggiGameDao(connector);
        this.positionDao = new JdbcJanggiPositionDao(connector);
        this.historyDao = new JdbcMoveHistoryDao(connector);
    }

    public void startGame(BoardArrangementStrategy strategyOfCho, BoardArrangementStrategy strategyOfHan) {
        gameDao.deleteAll();
        gameDao.addGame(strategyOfCho, strategyOfHan);
    }

    public boolean isPreviousGameNotOver() {
        return gameDao.getGame() != -1;
    }

    public void finishGame() {
        gameDao.deleteAll();
    }

    public List<List<JanggiPosition>> getHistories() {
        int gameId = gameDao.getGame();
        List<List<Integer>> histories = historyDao.getAllHistory(gameId);
        List<List<JanggiPosition>> parsedHistories = new ArrayList<>();

        for (List<Integer> history : histories) {
            JanggiPosition origin = positionDao.findPositionById(history.get(0));
            JanggiPosition destination = positionDao.findPositionById(history.get(1));
            parsedHistories.add(List.of(origin, destination));
        }

        return parsedHistories;
    }

    public BoardArrangementStrategy getChoStrategy() {
        int gameId = gameDao.getGame();
        return gameDao.findChoStrategyById(gameId);
    }

    public BoardArrangementStrategy getHanStrategy() {
        int gameId = gameDao.getGame();
        return gameDao.findHanStrategyById(gameId);
    }

    public void addHistory(JanggiPosition origin, JanggiPosition destination) {
        int gameId = gameDao.getGame();
        int originId = positionDao.findByPosition(origin);
        int destinationId = positionDao.findByPosition(destination);
        historyDao.addHistory(gameId, originId, destinationId);
    }
}
