package domain;

import domain.board.JanggiBoard;

import java.util.function.Consumer;
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
            retry(board, this::moveCommand);
            convertCountry();
        }
    }

    private void moveCommand(JanggiBoard board){
        outputView.printJanggiBoard(board);
        JanggiCoordinate originCoordinate = retryUntilValid(() -> inputView.readMovePiece(currentTurn.getCountryName()));
        board.validateOriginCoordinate(originCoordinate, currentTurn);
        JanggiCoordinate destinationCoordinate = retryUntilValid(inputView::readMoveDestination);
        board.movePiece(originCoordinate, destinationCoordinate);
    }

    private void convertCountry(){
        currentTurn = Country.convertTurn(currentTurn);
    }

    private <T> void retry(T value ,Consumer<T> consumer ) {

        while (true) {
            try {
                consumer.accept(value);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
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
