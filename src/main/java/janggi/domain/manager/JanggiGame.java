package janggi.domain.manager;

import janggi.database.utils.DatabaseUtils;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.dao.JanggiBoardDAO;
import janggi.domain.board.dao.JanggiBoardDAOImpl;
import janggi.domain.board.dao.TeamDAO;
import janggi.domain.board.dao.TeamDAOImpl;
import janggi.domain.board.dao.TurnDAO;
import janggi.domain.board.dao.TurnDAOImpl;
import janggi.domain.service.JanggiGameService;
import janggi.domain.setting.AssignType;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiGame {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameService janggiGameService;

    public JanggiGame(final InputView inputView, final OutputView outputView, final DatabaseUtils databaseUtils) {
        this.inputView = inputView;
        this.outputView = outputView;
        final JanggiBoardDAO janggiBoardDAO = new JanggiBoardDAOImpl(databaseUtils);
        final TurnDAO turnDAO = new TurnDAOImpl(databaseUtils);
        final TeamDAO teamDAO = new TeamDAOImpl(databaseUtils);
        this.janggiGameService = new JanggiGameService(janggiBoardDAO, turnDAO, teamDAO);
    }

    public void start() {
        outputView.writeStartMessage();

        boolean hasExistingGame = janggiGameService.hasExistingGame();

        JanggiBoard janggiBoard;
        if (hasExistingGame) {
            janggiBoard = janggiGameService.loadBoard();
        } else {
            janggiGameService.initializeGame();
            AssignType choAnswer = inputView.readAnswer(CampType.CHO);
            AssignType hanAnswer = inputView.readAnswer(CampType.HAN);
            janggiBoard = janggiGameService.createBoard(choAnswer, hanAnswer);
            janggiGameService.saveBoardState(janggiBoard);
        }

        CampType currentCampType = janggiGameService.getCurrentTurn();
        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
        outputView.writeStart(currentCampType.getName());

        do {
            currentCampType = janggiGameService.getCurrentTurn();
        } while (!isTurnOver(currentCampType, janggiBoard));

        outputView.writeTotalScore(janggiBoard.requestChoTotalScore(), janggiBoard.requestHanTotalScore());
        janggiGameService.clearGameData();
    }


    private boolean isTurnOver(CampType currentCampType, JanggiBoard janggiBoard) {
        if (currentCampType == CampType.CHO) {
            return processTurn(janggiBoard, CampType.CHO, 2, 1);
        } else {
            return processTurn(janggiBoard, CampType.HAN, 1, 2);
        }
    }

    private boolean processTurn(JanggiBoard janggiBoard, CampType campType, int enemyId, int teamId) {
        outputView.writeTurn(campType);

        boolean isValid = false;
        while (!isValid) {
            isValid = handleMoveException(() -> {
                JanggiPosition movedPiecePosition = inputView.readMovedPiecePosition();
                JanggiPosition destination = inputView.readDestinationPosition();
                janggiBoard.startTurn(movedPiecePosition, destination, campType);
                janggiGameService.deletePieceRecord(destination, enemyId);
                janggiGameService.updatePiecePosition(movedPiecePosition, destination, teamId);
            });
        }

        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());

        return isGameOver(janggiBoard, campType);
    }

    private boolean isGameOver(JanggiBoard janggiBoard, CampType campType) {
        boolean isGameOver;
        if (campType == CampType.CHO) {
            isGameOver = janggiBoard.isHanCampCollapse();
        } else {
            isGameOver = janggiBoard.isChoCampCollapse();
        }

        if (!isGameOver) {
            CampType nextTurn;
            if (campType == CampType.CHO) {
                nextTurn = CampType.HAN;
            } else {
                nextTurn = CampType.CHO;
            }
            janggiGameService.updateTurn(nextTurn);
        }

        return isGameOver;
    }

    private boolean handleMoveException(Runnable action) {
        try {
            action.run();
            return true;
        } catch (IllegalArgumentException e) {
            outputView.writeErrorMessage(e.getMessage());
            return false;
        }
    }
}
