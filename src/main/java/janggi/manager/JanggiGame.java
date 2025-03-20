package janggi.manager;

import janggi.board.JanggiBoard;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.Position;
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
            playTurn(janggiBoard, CampType.CHO);
            playTurn(janggiBoard, CampType.HAN);
        }
    }

    private void playTurn(JanggiBoard janggiBoard, CampType campType) {
        outputView.writeTurn(campType);
        Position movedPiecePosition = inputView.readMovedPiecePosition();
        Position destination = inputView.readDestinationPosition();
        janggiBoard.movePiece(campType, movedPiecePosition, destination);
        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
    }

}
