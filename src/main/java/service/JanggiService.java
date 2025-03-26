package service;

import domain.dao.JanggiGameDao;
import domain.dao.JanggiPositionDao;
import domain.dao.MoveHistoryDao;
import domain.janggiboard.JanggiBoard;
import domain.janggiboard.JanggiBoardBasicInitializer;
import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.piece.JanggiSide;
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

    public JanggiBoard createJanggiBoard(BoardArrangementStrategy strategyOfCho, BoardArrangementStrategy strategyOfHan) {
        gameDao.deleteAll();
        gameDao.addGame(strategyOfCho, strategyOfHan);

        return new JanggiBoard(new JanggiBoardBasicInitializer(strategyOfCho, strategyOfHan));
    }

    public boolean isPreviousGameNotOver() {
        return !gameDao.getGame().equals("-1");
    }

    public JanggiBoard loadPreviousGameBoard() {
        BoardArrangementStrategy strategyOfCho = getChoStrategy();
        BoardArrangementStrategy strategyOfHan = getHanStrategy();
        JanggiBoard board = new JanggiBoard(new JanggiBoardBasicInitializer(strategyOfCho, strategyOfHan));

        List<List<JanggiPosition>> histories = getHistories();
        for (List<JanggiPosition> history : histories) {
            JanggiPosition origin = history.get(0);
            JanggiPosition destination = history.get(1);
            board.movePiece(origin, destination);
        }
        return board;
    }

    public void movePiece(JanggiBoard board, List<JanggiPosition> originAndDestination, JanggiSide side) {
        JanggiPosition origin = originAndDestination.get(0);
        JanggiPosition destination = originAndDestination.get(1);
        if (!board.isSameTeam(origin, side)) {
            throw new IllegalArgumentException("차례에 맞는 말을 선택하세요.");
        }
        board.movePiece(origin, destination);
        addHistory(origin, destination);
    }

    public void finishGame() {
        gameDao.deleteAll();
    }

    public boolean isGameEnd(JanggiBoard board, JanggiSide nowTurn) {
        return board.isOppositeKingCaptured(nowTurn);
    }

    private List<List<JanggiPosition>> getHistories() {
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

    private BoardArrangementStrategy getChoStrategy() {
        String gameId = gameDao.getGame();
        return gameDao.findChoStrategyById(gameId);
    }

    private BoardArrangementStrategy getHanStrategy() {
        String gameId = gameDao.getGame();
        return gameDao.findHanStrategyById(gameId);
    }

    private void addHistory(JanggiPosition origin, JanggiPosition destination) {
        String gameId = gameDao.getGame();
        String originId = positionDao.findByPosition(origin);
        String destinationId = positionDao.findByPosition(destination);
        historyDao.addHistory(gameId, originId, destinationId);
    }
}
