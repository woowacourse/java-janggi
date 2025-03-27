package domain.game;

import domain.Coordinate;
import domain.board.Board;
import domain.board.BoardSettingUpStrategy;
import domain.board.strategy.MaSangMaSang;
import domain.board.strategy.MaSangSangMa;
import domain.board.strategy.SangMaMaSang;
import domain.board.strategy.SangMaSangMa;
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
            showScore(board);
            nextTurn();
        }
    }

    private Board settingUp() {
        BoardSettingUpStrategy hanSettingUpStrategy = retryUntilValid(() -> {
            String settingUp = inputView.readSettingUp(Country.HAN);
            return getBoardSettingUpStrategy(settingUp);
        });

        BoardSettingUpStrategy choSettingUpStrategy = retryUntilValid(() -> {
            String settingUp = inputView.readSettingUp(Country.CHO);
            return getBoardSettingUpStrategy(settingUp);
        });

        return new Board(hanSettingUpStrategy, choSettingUpStrategy);
    }

    private BoardSettingUpStrategy getBoardSettingUpStrategy(String settingUp) {
        return switch (settingUp) {
            case SangMaMaSang.SANG_MA_MA_SANG -> new SangMaMaSang();
            case MaSangSangMa.MA_SANG_SANG_MA -> new MaSangMaSang();
            case SangMaSangMa.SANG_MA_SANG_MA -> new SangMaSangMa();
            case MaSangMaSang.MA_SANG_MA_SANG -> new MaSangSangMa();
            default -> throw new IllegalArgumentException("[ERROR] 상차림 전략을 다시 입력해주세요.");
        };
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

    private void showScore(Board board) {
        int hanScore = board.calculateHanScore();
        int choScore = board.calculateChoScore();

        outputView.printScore(hanScore, choScore);
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
