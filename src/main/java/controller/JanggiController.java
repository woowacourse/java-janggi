package controller;

import domain.JanggiBoard;
import domain.piece.Piece;
import domain.position.Position;
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
            try {
                outputView.printBoard(janggiBoard);
                Position from = inputMovePosition();
                Position to = inputTargetPosition();
                Piece currentPiece = janggiBoard.getPiece(from);
                boolean movePiece = currentPiece.canMove(from, to, janggiBoard);
                if (!movePiece) {
                    throw new IllegalArgumentException("[ERROR] 해당 위치로 이동할 수 없는 기물입니다.");
                }
                janggiBoard.move(from, to, currentPiece);
                outputView.printBoard(janggiBoard);
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e);
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
