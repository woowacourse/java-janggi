package domain.game;

import domain.Coordinate;
import domain.board.Board;
import domain.board.SettingUp;
import domain.piece.Country;
import java.util.function.Consumer;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiGame {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    private Country currentTurn = Country.HAN;

    public void start() {
        Board board = settingUp();

        while (true) {
            takeTurn(board, this::moveCommand);
            nextTurn();
        }
    }

    private Board settingUp() {
        Board board = new Board();

        SettingUp settingUpHan = retryUntilValid(() -> inputView.readSettingUp(currentTurn));
        board.setUpHan(settingUpHan);

        nextTurn();

        SettingUp settingUpCho = retryUntilValid(() -> inputView.readSettingUp(currentTurn));
        board.setUpCho(settingUpCho);

        return board;
    }

    private void moveCommand(Board board) {
        outputView.printJanggiBoard(board);

        Coordinate originCoordinate = retryUntilValid(() ->
                inputView.readMovePiece(currentTurn.getCountryName()));

        board.validateOriginCoordinate(originCoordinate, currentTurn);

        Coordinate destinationCoordinate = retryUntilValid(inputView::readMoveDestination);

        board.movePiece(originCoordinate, destinationCoordinate);
    }

    private void nextTurn() {
        currentTurn = currentTurn.convertTurn();
    }

    private <T> void takeTurn(T value, Consumer<T> consumer) {
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
