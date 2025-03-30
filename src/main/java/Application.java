import board.Board;
import board.creator.MaSangMaSangCreator;
import board.creator.MaSangSangMaCreator;
import board.creator.SangMaMaSangCreator;
import board.creator.SangMaSangMaCreator;
import board.creator.TableSettingCreator;
import java.util.Map;
import java.util.function.Supplier;
import team.Country;
import view.InputView;
import view.InputView.CoordinatesPair;
import view.OutputView;

public class Application {

    private static final Map<Integer, TableSettingCreator> boardCreateStrategy = Map.of(
            1, new MaSangSangMaCreator(),
            2, new MaSangMaSangCreator(),
            3, new SangMaSangMaCreator(),
            4, new SangMaMaSangCreator()
    );

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        Board board = initializeBoard();
        outputView.printBoard(board.getUnmodifiablePieces());

        startGame(board);
        judgeWinner(board);
    }

    private Board initializeBoard() {
        int hanTableSetting = executeWithRetry(() -> inputView.readTableSetting(Country.HAN));
        int choTableSetting = executeWithRetry(() -> inputView.readTableSetting(Country.CHO));
        return Board.create(
                boardCreateStrategy.get(hanTableSetting),
                boardCreateStrategy.get(choTableSetting)
        );
    }

    private void startGame(Board board) {
        while (!board.isEnd()) {
            for (Country country : Country.values()) {
                executeWithRetry(() -> playTurn(board, country));
            }
        }
    }

    private void playTurn(Board board, Country country) {
        CoordinatesPair coordinatesPair = inputView.readMoveCoordinate(country);
        board.move(country, coordinatesPair.departure(), coordinatesPair.arrival());
        outputView.printBoard(board.getUnmodifiablePieces());
        outputView.printScore(board.getUnmodifiableScores());
    }

    private void judgeWinner(Board board) {
        outputView.printWinner(board.judgeWinner());
    }

    private void executeWithRetry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                return;
            } catch (RuntimeException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private <T> T executeWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (RuntimeException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
