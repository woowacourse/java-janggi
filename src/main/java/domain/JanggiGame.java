package domain;

import domain.board.Board;
import domain.board.strategy.SangMaMaSang;
import domain.piece.Country;
import java.util.function.Consumer;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    private Country currentTurn;

    public void start() {
        Board board = new Board(new SangMaMaSang());
        currentTurn = Country.HAN;

        while (true) {
            operateTurn(board, this::moveCommand);
            convertCountry();
        }
    }

    private void moveCommand(Board board) {
        outputView.printJanggiBoard(board);
        Coordinate originCoordinate = retryUntilValid(
                () -> inputView.readMovePiece(currentTurn.getCountryName()));
        board.validateOriginCoordinate(originCoordinate, currentTurn);
        Coordinate destinationCoordinate = retryUntilValid(inputView::readMoveDestination);
        board.movePiece(originCoordinate, destinationCoordinate);
    }

    private void convertCountry() {
        currentTurn = Country.convertTurn(currentTurn);
    }

    private <T> void operateTurn(T value, Consumer<T> consumer) {
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
