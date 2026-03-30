package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Board board = new Board();
        board.initialize();
        boolean isChoTurn = true;

        outputView.printBoard(board.getBoard());

        while(true) {
            outputView.printTurnMessage(isChoTurn);

            outputView.printMoveInfo();
            Position movePiecePosition = inputView.readPosition();

            List<Position> availablePositions = board.findAvailablePositions(movePiecePosition);
            outputView.printAvailablePositions(board.getBoard(), availablePositions);

            outputView.printMoveChoiceInfo();
            Position movePosition = inputView.readPosition();

            board.movePiece(movePiecePosition, movePosition);

            outputView.printBoard(board.getBoard());

            isChoTurn = !isChoTurn;
        }
    }
}
