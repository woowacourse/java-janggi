package janggi.controller;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

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

            Position movePiecePosition = doLoop(() -> {
                outputView.printMoveInfo();
                Position position = inputView.readPosition();
                board.findAvailablePositions(position);
                return position;
            });

            List<Position> availablePositions =  board.findAvailablePositions(movePiecePosition);

            outputView.printAvailablePositions(board.getBoard(), availablePositions);

            Position movePosition = doLoop(()->{
                outputView.printMoveChoiceInfo();
                Position position = inputView.readPosition();
                board.validateDestination(movePiecePosition, position);
                return position;
            });

            board.movePiece(movePiecePosition, movePosition);

            outputView.printBoard(board.getBoard());

            isChoTurn = !isChoTurn;
        }
    }

    private <T> T doLoop(Supplier<T> inputFunction) {
        while (true) {
            try {
                return inputFunction.get();
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
