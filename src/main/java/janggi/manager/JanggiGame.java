package janggi.manager;

import janggi.board.JanggiBoard;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        outputView.writeStartMessage();

        AssignType choAnswer = inputView.readAnswer(CampType.CHO);
        AssignType hanAnswer = inputView.readAnswer(CampType.HAN);

        final JanggiBoard janggiBoard = new JanggiBoard(choAnswer, hanAnswer);
        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
        outputView.writeChoStart();

        while (true) {
            boolean isChoNotCollapse = playChoTurn(janggiBoard);
            if (!isChoNotCollapse) {
                break;
            }
            boolean isHanNotCollapse = playHanTurn(janggiBoard);
            if (!isHanNotCollapse) {
                break;
            }
        }

        outputView.writeTotalScore(janggiBoard.requestChoTotalScore(), janggiBoard.requestHanTotalScore());
    }

    private boolean playHanTurn(final JanggiBoard janggiBoard) {
        outputView.writeTurn(CampType.HAN);

        boolean isValid = false;
        while (!isValid) {
            isValid = handleMoveException(() -> {
                JanggiPosition movedPieceJanggiPosition = inputView.readMovedPiecePosition();
                JanggiPosition destination = inputView.readDestinationPosition();
                janggiBoard.startTurn(movedPieceJanggiPosition, destination, CampType.HAN);
            });
        }

        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());

        return !janggiBoard.isChoCampCollapse();
    }

    private boolean playChoTurn(final JanggiBoard janggiBoard) {
        outputView.writeTurn(CampType.CHO);

        boolean isValid = false;
        while (!isValid) {
            isValid = handleMoveException(() -> {
                JanggiPosition movedPieceJanggiPosition = inputView.readMovedPiecePosition();
                JanggiPosition destination = inputView.readDestinationPosition();
                janggiBoard.startTurn(movedPieceJanggiPosition, destination, CampType.CHO);
            });
        }

        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());

        return !janggiBoard.isHanCampCollapse();
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
