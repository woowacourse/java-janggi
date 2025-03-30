package janggi.game;

import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.TurnMenuAnswer;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        JanggiBoard janggiBoard = prepareGame();

        CampType campTypeInturn = CampType.HAN;
        while (true) {
            campTypeInturn = campTypeInturn.getEnemyCampType();
            TurnMenuAnswer turnMenuAnswer = readTurnMenuAnswer(campTypeInturn);
            if (turnMenuAnswer == TurnMenuAnswer.ONE) {
                movePiece(janggiBoard, campTypeInturn);
                if (janggiBoard.isGameEnd()) {
                    break;
                }
            }
            if (turnMenuAnswer == TurnMenuAnswer.TWO) {
                continue;
            }
            if (turnMenuAnswer == TurnMenuAnswer.THREE) {
                break;
            }
        }
        printGameResult(janggiBoard);
    }

    private JanggiBoard prepareGame() {
        outputView.writeStartMessage();
        PieceAssignType choAnswer = readPieceAssignType(CampType.CHO);
        PieceAssignType hanAnswer = readPieceAssignType(CampType.HAN);
        JanggiBoard janggiBoard = new JanggiBoard(choAnswer, hanAnswer);
        printJanggiBoardState(janggiBoard);
        return janggiBoard;
    }

    private void movePiece(JanggiBoard janggiBoard, CampType campType) {
        while (true) {
            try {
                outputView.writeTurn(campType);
                Position movedPiecePosition = inputView.readMovedPiecePosition();
                Position destination = inputView.readDestinationPosition();
                janggiBoard.movePiece(campType, movedPiecePosition, destination);
                printJanggiBoardState(janggiBoard);
                return;
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private PieceAssignType readPieceAssignType(CampType campType) {
        while (true) {
            try {
                return inputView.readPieceAssignType(campType);
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private TurnMenuAnswer readTurnMenuAnswer(CampType campType) {
        while (true) {
            try {
                return inputView.readTurnMenuAnswer(campType);
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private void printJanggiBoardState(JanggiBoard board) {
        outputView.writeScore(board.getScore(CampType.CHO), board.getScore(CampType.HAN));
        outputView.writeJanggiBoard(board.getPieces(CampType.CHO), board.getPieces(CampType.HAN));
    }

    private void printGameResult(JanggiBoard board) {
        outputView.writeGameEndMessage();
        outputView.writeWinning(board.whoWin());
    }
}
