package domain;

import domain.board.JanggiBoard;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void start() {
        JanggiBoard board = new JanggiBoard();

        outputView.printJanggiBoard(board);
        JanggiCoordinate originCoordinate = retryUntilValid(inputView::readMovePiece);
        JanggiCoordinate destinationCoordinate = retryUntilValid(inputView::readMoveDestination);

        board.movePiece(originCoordinate, destinationCoordinate);

        
    }

    private <T> T retryUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
