package janggi.manager;

import janggi.board.JanggiBoard;
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

        // TODO: 현재 단계에서는 종료조건 없음, 2단계에서 승패 구현할때 구현 예정
        for (int i = 0; i < 10; i++) {
            playTurn(janggiBoard, CampType.CHO);
            playTurn(janggiBoard, CampType.HAN);
        }
    }

    private void playTurn(final JanggiBoard janggiBoard, final CampType campType) {
        outputView.writeTurn(campType);

        while (true) {
            try {
                JanggiPosition movedPieceJanggiPosition = findMovedPieceJanggiPosition();
                JanggiPosition destination = findDestinationJanggiPosition();

                janggiBoard.movePiece(campType, movedPieceJanggiPosition, destination);

                outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
                break;
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.writeErrorMessage(illegalArgumentException.getMessage());
            }
        }
    }

    private JanggiPosition findMovedPieceJanggiPosition() {
        while (true) {
            try {
                return inputView.readMovedPiecePosition();
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.writeErrorMessage(illegalArgumentException.getMessage());
            }
        }
    }

    private JanggiPosition findDestinationJanggiPosition() {
        while (true) {
            try {
                return inputView.readDestinationPosition();
            } catch (IllegalArgumentException illegalArgumentException) {
                outputView.writeErrorMessage(illegalArgumentException.getMessage());
            }
        }
    }

}
