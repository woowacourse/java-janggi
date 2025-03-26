package service;

import domain.dao.JanggiGameDao;
import domain.dao.JanggiPositionDao;
import domain.dao.MoveHistoryDao;
import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.position.JanggiPosition;
import java.util.ArrayList;
import java.util.List;
import util.ProductionDatabaseConnector;

public class JanggiService {

    private final JanggiGameDao gameDao;
    private final JanggiPositionDao positionDao;
    private final MoveHistoryDao historyDao;

    public JanggiService() {
        ProductionDatabaseConnector connector = new ProductionDatabaseConnector();
        this.gameDao = new JanggiGameDao(connector);
        this.positionDao = new JanggiPositionDao(connector);
        this.historyDao = new MoveHistoryDao(connector);
    }

    public boolean isPreviousGameNotOver() {
        return !gameDao.getGame().equals("-1");
    }

    public List<List<JanggiPosition>> getHistories() {
        String gameId = gameDao.getGame();
        List<List<String>> histories = historyDao.getAllHistory(gameId);
        List<List<JanggiPosition>> parsedHistories = new ArrayList<>();

        for (List<String> history : histories) {
            JanggiPosition origin = positionDao.findPositionById(history.get(0));
            JanggiPosition destination = positionDao.findPositionById(history.get(1));
            parsedHistories.add(List.of(origin, destination));
        }

        return parsedHistories;
    }

    public BoardArrangementStrategy getChoStrategy() {
        String gameId = gameDao.getGame();
        return gameDao.findChoStrategyById(gameId);
    }

    public BoardArrangementStrategy getHanStrategy() {
        String gameId = gameDao.getGame();
        return gameDao.findHanStrategyById(gameId);
    }

    public void startGame(BoardArrangementStrategy strategyOfCho, BoardArrangementStrategy strategyOfHan) {
        gameDao.deleteAll();
        gameDao.addGame(strategyOfCho, strategyOfHan);
    }

    public void addHistory(JanggiPosition origin, JanggiPosition destination) {
        String gameId = gameDao.getGame();
        String originId = positionDao.findByPosition(origin);
        String destinationId = positionDao.findByPosition(destination);
        historyDao.addHistory(gameId, originId, destinationId);
    }

    public void finishGame() {
        gameDao.deleteAll();
    }
}
