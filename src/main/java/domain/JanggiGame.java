package domain;

import domain.board.JanggiBoard;
import java.util.function.Supplier;

import domain.piece.Country;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    private Country currentTurn;

    public void start() {
        JanggiBoard board = new JanggiBoard();
        currentTurn = Country.HAN;

        while(true){
            outputView.printJanggiBoard(board);
            JanggiCoordinate originCoordinate = retryUntilValid(() -> inputView.readMovePiece(currentTurn.name()));
            JanggiCoordinate destinationCoordinate = retryUntilValid(inputView::readMoveDestination);

            board.movePiece(originCoordinate, destinationCoordinate);
            convertCountry();
        }
    }

    void convertCountry(){
        currentTurn = Country.convertTurn(currentTurn);
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
