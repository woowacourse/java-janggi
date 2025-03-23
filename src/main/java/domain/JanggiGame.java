package domain;

import domain.board.Board;
import domain.board.BoardSettingUpStrategy;
import domain.board.SettingUp;
import domain.piece.Country;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
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
        Map<Coordinate, Piece> boardSetup = new HashMap<>(BoardSettingUpStrategy.setUp());

        SettingUp settingUpHan = retryUntilValid(() -> inputView.readSettingUp(currentTurn));
        BoardSettingUpStrategy hanStrategy = settingUpHan.getStrategy();
        boardSetup.putAll(hanStrategy.setUpHanByStrategy());

        nextTurn();

        SettingUp settingUpCho = retryUntilValid(() -> inputView.readSettingUp(currentTurn));
        BoardSettingUpStrategy choStrategy = settingUpCho.getStrategy();
        boardSetup.putAll(choStrategy.setUpChoByStrategy());

        return new Board(boardSetup);
    }

    private void moveCommand(Board board) {
        outputView.printJanggiBoard(board);
        Coordinate originCoordinate = retryUntilValid(
                () -> inputView.readMovePiece(currentTurn.getCountryName()));
        board.validateOriginCoordinate(originCoordinate, currentTurn);
        Coordinate destinationCoordinate = retryUntilValid(inputView::readMoveDestination);
        board.movePiece(originCoordinate, destinationCoordinate);
    }

    private void nextTurn() {
        currentTurn = Country.convertTurn(currentTurn);
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
