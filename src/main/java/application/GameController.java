package application;

import static domain.piece.Camp.CHO;
import static domain.piece.Camp.HAN;

import domain.board.SetUp;
import domain.game.Game;
import domain.piece.Camp;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import repository.GameRepository;
import view.GameCommand;
import view.InputView;
import view.OutputView;

public class GameController {
    private static final Map<Integer, SetUp> INPUT_MAP = Map.of(
            1, SetUp.LEFT_ELEPHANT,
            2, SetUp.RIGHT_ELEPHANT,
            3, SetUp.INNER_ELEPHANT,
            4, SetUp.OUTER_ELEPHANT
    );
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public GameController(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void run() {
        Game game = loadGame();
        outputView.printBoard(game.board());
        playJanggi(game);
    }

    private Game loadGame() {
        Optional<Game> savedGame = gameRepository.findInProgressGame();

        if (savedGame.isEmpty()) {
            return createNewGame();
        }

        if (askContinue()) {
            return savedGame.get();
        }

        return createNewGame();
    }

    private boolean askContinue() {
        while (true) {
            outputView.printResumePrompt();

            try {
                return inputView.readContinueAnswer();
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private Game createNewGame() {
        outputView.printSetUpOptions();
        SetUp hanSetUp = readSetUp(HAN);
        SetUp choSetUp = readSetUp(CHO);

        Game game = new Game(choSetUp, hanSetUp);
        gameRepository.save(game);
        return game;
    }

    private void playJanggi(Game game) {
        while (!game.isFinished()) {
            outputView.printTurnPrompt(game.currentTurn());

            try {
                GameCommand command = inputView.readCommand();
                command.execute(game);
                gameRepository.save(game);
                outputView.printBoard(game.board());
                outputView.printScore(game.scoreOf(Camp.CHO), game.scoreOf(Camp.HAN));
            } catch (IllegalArgumentException | NoSuchElementException exception) {
                outputView.printError(exception.getMessage());
            } catch (IllegalStateException exception) {
                return;
            }
        }

        outputView.printWinner(game.currentTurn());
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
