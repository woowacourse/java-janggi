package application;

import static domain.piece.Camp.CHO;
import static domain.piece.Camp.HAN;

import domain.board.SetUp;
import domain.game.Game;
import domain.piece.Camp;
import java.util.Map;
import java.util.NoSuchElementException;
import view.GameCommand;
import view.InputView;
import view.OutputView;

public class Application {
    private static final Map<Integer, SetUp> INPUT_MAP = Map.of(
            1, SetUp.LEFT_ELEPHANT,
            2, SetUp.RIGHT_ELEPHANT,
            3, SetUp.INNER_ELEPHANT,
            4, SetUp.OUTER_ELEPHANT
    );
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        new Application().run();
    }

    public void run() {
        outputView.printSetUpOptions();
        SetUp hanSetUp = readSetUp(HAN);
        SetUp choSetUp = readSetUp(CHO);

        Game game = new Game(choSetUp, hanSetUp);
        outputView.printBoard(game.board());

        playJanggi(game);
    }

    private void playJanggi(Game game) {
        while (true) {
            outputView.printTurnPrompt(game.currentTurn());

            try {
                GameCommand command = inputView.readCommand();
                command.execute(game);
                outputView.printBoard(game.board());
            } catch (IllegalArgumentException | NoSuchElementException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalStateException exception) {
                return;
            }
        }
    }

    private SetUp readSetUp(Camp camp) {
        while (true) {
            outputView.printSetUpPrompt(camp);

            try {
                return toSetUp(inputView.readSetUpNumber());
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    public static SetUp toSetUp(int input) {
        SetUp setUp = INPUT_MAP.get(input);
        if (setUp == null) {
            throw new IllegalArgumentException("[ERROR] 상차림 번호는 1, 2, 3, 4 중 하나여야 합니다.");
        }

        return setUp;
    }
}
