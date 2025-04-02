package service;

import domain.dao.JdbcJanggiGameDao;
import domain.dao.JdbcJanggiPositionDao;
import domain.dao.JdbcMoveHistoryDao;
import domain.dto.HistoryDto;
import domain.janggiboard.JanggiBoard;
import domain.janggiboard.JanggiBoardBasicInitializer;
import domain.janggiboard.customstrategy.BoardArrangementStrategy;
import domain.piece.JanggiPiece;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import util.ProductionDatabaseConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiService {

    private final JdbcJanggiGameDao gameDao;
    private final JdbcJanggiPositionDao positionDao;
    private final JdbcMoveHistoryDao historyDao;
    private JanggiBoard board;

    public JanggiService() {
        ProductionDatabaseConnector connector = new ProductionDatabaseConnector();
        this.gameDao = new JdbcJanggiGameDao(connector);
        this.positionDao = new JdbcJanggiPositionDao(connector);
        this.historyDao = new JdbcMoveHistoryDao(connector);
    }

    public void startGame(BoardArrangementStrategy strategyOfCho, BoardArrangementStrategy strategyOfHan) {
        gameDao.deleteAll();
        gameDao.addGame(strategyOfCho, strategyOfHan);
        board = new JanggiBoard(new JanggiBoardBasicInitializer(strategyOfCho, strategyOfHan));
    }

    public boolean isPreviousGameNotOver() {
        return gameDao.getGame() != -1;
    }

    public void finishGame() {
        gameDao.deleteAll();
    }

    public void loadPreviousGameBoard() {
        BoardArrangementStrategy strategyOfCho = getChoStrategy();
        BoardArrangementStrategy strategyOfHan = getHanStrategy();
        board = new JanggiBoard(new JanggiBoardBasicInitializer(strategyOfCho, strategyOfHan));

        for (List<JanggiPosition> history : getHistories()) {
            JanggiPosition origin = history.get(0);
            JanggiPosition destination = history.get(1);
            board.movePiece(origin, destination);
        }
    }

    private List<List<JanggiPosition>> getHistories() {
        int gameId = gameDao.getGame();
        List<HistoryDto> histories = historyDao.getAllHistory(gameId);
        List<List<JanggiPosition>> parsedHistories = new ArrayList<>();

        for (HistoryDto history : histories) {
            JanggiPosition origin = positionDao.findPositionById(history.originId());
            JanggiPosition destination = positionDao.findPositionById(history.destinationId());
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

    public Map<JanggiPosition, JanggiPiece> getBoard() {
        return board.getBoard();
    }

    public boolean isOppositeKingCaptured(final JanggiSide nowTurn) {
        return board.isOppositeKingCaptured(nowTurn);
    }

    public void movePiece(final List<JanggiPosition> originAndDestination, final JanggiSide side) {
        JanggiPosition origin = originAndDestination.get(0);
        JanggiPosition destination = originAndDestination.get(1);
        if (!board.isSameTeam(origin, side)) {
            throw new IllegalArgumentException("차례에 맞는 말을 선택하세요.");
        }
        try {
            board.movePiece(origin, destination);
            addHistory(origin, destination);
        } catch (IllegalStateException e) {
            board.movePiece(destination, origin);
        }
    }

    public void addHistory(JanggiPosition origin, JanggiPosition destination) {
        int gameId = gameDao.getGame();
        int originId = positionDao.findByPosition(origin);
        int destinationId = positionDao.findByPosition(destination);
        historyDao.addHistory(gameId, originId, destinationId);
    }

    public int getRemainingPiecesTotalScore(final JanggiSide janggiSide) {
        return board.getRemainingPiecesTotalScore(janggiSide);
    }
}
