package janggi.manager;

import janggi.board.JanggiBoard;
import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
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
        // TODO: 현재 단계에서는 종료조건 없음, 2단계에서 승패 구현할때 구현 예정
        JanggiBoard janggiBoard = prepareGame();
        for (int i = 0; i < 3; i++) {
            playTurn(janggiBoard, CampType.CHO);
            playTurn(janggiBoard, CampType.HAN);
        }
    }

    private JanggiBoard prepareGame() {
        outputView.writeStartMessage();
        PieceAssignType choAnswer = inputView.readPieceAssignType(CampType.CHO);
        PieceAssignType hanAnswer = inputView.readPieceAssignType(CampType.HAN);
        JanggiBoard janggiBoard = new JanggiBoard(choAnswer, hanAnswer);
        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
        return janggiBoard;
    }

    private void playTurn(JanggiBoard janggiBoard, CampType campType) {
        outputView.writeTurn(campType);
        Position movedPiecePosition = inputView.readMovedPiecePosition();
        Position destination = inputView.readDestinationPosition();
        janggiBoard.movePiece(campType, movedPiecePosition, destination);
        outputView.writeJanggiBoard(janggiBoard.getChoPieces(), janggiBoard.getHanPieces());
    }

}
