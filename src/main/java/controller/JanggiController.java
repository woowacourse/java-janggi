package controller;

import domain.JanggiBoard;
import domain.piece.Piece;
import domain.position.Position;
import java.util.LinkedHashMap;
import view.InputView;
import view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        JanggiBoard janggiBoard = new JanggiBoard();
        while (true) {
            outputView.printBoard(janggiBoard);
            Position currentPosition = inputMovePosition();
            Position targetPosition = inputTargetPosition();
            Piece currentPiece = janggiBoard.getPiece(currentPosition);
            boolean movePiece = currentPiece.canMove(currentPosition, targetPosition, janggiBoard);
            if (movePiece) {
                janggiBoard.move(currentPosition, targetPosition, currentPiece);
                outputView.printBoard(janggiBoard);
            }
        }
    }

    private Position inputMovePosition() {
        String inputMovePosition = inputView.inputMovePiece();
        String[] parts = inputMovePosition.split(",");

        int row = Integer.parseInt(parts[0].trim());
        int column = Integer.parseInt(parts[1].trim());

        return new Position(row, column);
    }

    private Position inputTargetPosition() {
        String inputTargetPosition = inputView.inputTargetPosition();
        String[] parts = inputTargetPosition.split(",");

        int row = Integer.parseInt(parts[0].trim());
        int column = Integer.parseInt(parts[1].trim());

        return new Position(row, column);
    }
}
