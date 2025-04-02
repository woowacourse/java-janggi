package janggi.manager;

import janggi.board.JanggiBoard;
import janggi.service.JanggiGameService;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiGame {
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameService janggiGameService;

    public JanggiGame(final InputView inputView, final OutputView outputView, final JanggiGameService janggiGameService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiGameService = janggiGameService;
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
