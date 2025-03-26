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

        while (!isEndGame(board)) {
            takeTurn(board, this::movePiece);
            nextTurn();
        }
    }

    private Board settingUp() {
        String settingUpHan = retryUntilValid(() -> inputView.readSettingUp(Country.HAN));
        String settingUpCho = retryUntilValid(() -> inputView.readSettingUp(Country.CHO));

        return new Board(
                SettingUp.of(settingUpHan).getStrategy(),
                SettingUp.of(settingUpCho).getStrategy());
    }

    private void movePiece(Board board) {
        outputView.printJanggiBoard(board);

        Coordinate from = retryUntilValid(() -> inputView.readMoveFrom(currentTurn.getCountryName()));
        board.validateIsMyPiece(from, currentTurn);
        Coordinate to = retryUntilValid(inputView::readMoveTo);

        board.movePiece(from, to);
    }

    private boolean isEndGame(Board board) {
        boolean isChoGungDead = board.isChoGungDead();
        boolean isHanGungDead = board.isHanGungDead();
        if (isChoGungDead || isHanGungDead) {
            outputView.printEndGame(isChoGungDead, isHanGungDead);
            return true;
        }
        return false;
    }

    private void nextTurn() {
        currentTurn = currentTurn.convertCountry();
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
