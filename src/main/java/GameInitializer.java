import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

import domain.Board;
import domain.GameDeadline;
import domain.GameStatus;
import domain.JanggiGame;
import domain.TeamColor;
import domain.TurnManager;
import io.InputView;
import io.OutputView;
import persistence.GameStatePersister;
import persistence.SavedGameState;
import strategy.formation.InitialFormationStrategy;
import strategy.formation.InitialFormationStrategyFactory;

public class GameInitializer {

    private final InputView inputView;
    private final OutputView outputView;

    public GameInitializer(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public InitializedGame resolve(GameStatePersister persister, Clock clock) {
        Optional<SavedGameState> saved = persister.load();
        if (saved.isEmpty()) {
            return startFresh(clock);
        }
        return resumeOrNew(saved.get(), persister, clock);
    }

    private InitializedGame resumeOrNew(SavedGameState saved, GameStatePersister persister, Clock clock) {
        if (saved.gameStatus() == GameStatus.ENDED) {
            notifySavedGameEnded(saved.winner());
            return startFresh(clock);
        }
        int choice = readResumeChoiceWithRetry();
        if (choice == 1) {
            return resume(saved, persister, clock);
        }
        return startFresh(clock);
    }

    private void notifySavedGameEnded(TeamColor winner) {
        if (winner == null) {
            outputView.printSavedGameEndedWithoutWinner();
            return;
        }
        outputView.printSavedGameEndedWithWinner(winner);
    }

    private InitializedGame resume(SavedGameState saved, GameStatePersister persister, Clock clock) {
        Board board = new Board(saved.snapshot().pieces());
        TurnManager turnManager = new TurnManager(saved.currentTurn());
        JanggiGame game = new JanggiGame(board, turnManager);
        GameDeadline deadline = resolveDeadlineForResume(saved, persister, clock);
        return new InitializedGame(game, deadline);
    }

    private InitializedGame startFresh(Clock clock) {
        Board board = initializeNewBoard();
        GameDeadline deadline = readNewDeadline(clock);
        TurnManager turnManager = new TurnManager();
        turnManager.start();
        JanggiGame game = new JanggiGame(board, turnManager);
        return new InitializedGame(game, deadline);
    }

    private GameDeadline resolveDeadlineForResume(SavedGameState saved, GameStatePersister persister, Clock clock) {
        if (saved.deadline() != null) {
            return saved.deadline();
        }
        GameDeadline deadline = readNewDeadline(clock);
        persister.saveWithDeadline(saved, deadline);
        return deadline;
    }

    private GameDeadline readNewDeadline(Clock clock) {
        int seconds = readTimeLimitSecondsWithRetry();
        Instant deadlineInstant = Instant.now(clock).plusSeconds(seconds);
        return GameDeadline.of(deadlineInstant);
    }

    private int readTimeLimitSecondsWithRetry() {
        while (true) {
            try {
                return parseTimeLimitSeconds();
            } catch (RuntimeException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private int parseTimeLimitSeconds() {
        outputView.printTimeLimitPrompt();
        int seconds = inputView.readTimeLimitSeconds();
        return validatedSeconds(seconds);
    }

    private static int validatedSeconds(int seconds) {
        if (seconds <= 0) {
            throw new IllegalArgumentException("제한 시간은 1초 이상이어야 합니다.");
        }
        return seconds;
    }

    private int readResumeChoiceWithRetry() {
        while (true) {
            try {
                return parseResumeChoice();
            } catch (RuntimeException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private int parseResumeChoice() {
        outputView.printResumePrompt();
        int choice = inputView.readResumeOrNewChoice();
        return validatedResumeChoice(choice);
    }

    private static int validatedResumeChoice(int choice) {
        if (choice == 1) {
            return 1;
        }
        if (choice == 2) {
            return 2;
        }
        throw new IllegalArgumentException("1 또는 2만 입력하세요.");
    }

    private Board initializeNewBoard() {
        InitialFormationStrategy choStrategy = chooseFormationStrategy(TeamColor.CHO);
        InitialFormationStrategy hanStrategy = chooseFormationStrategy(TeamColor.HAN);
        Initializer initializer = new Initializer(choStrategy, hanStrategy);
        return initializer.initialize();
    }

    private InitialFormationStrategy chooseFormationStrategy(TeamColor teamColor) {
        while (true) {
            Optional<InitialFormationStrategy> picked = tryPickFormation(teamColor);
            if (picked.isPresent()) {
                return picked.get();
            }
        }
    }

    private Optional<InitialFormationStrategy> tryPickFormation(TeamColor teamColor) {
        outputView.printFormationSelectionPrompt(teamColor);
        try {
            return Optional.of(InitialFormationStrategyFactory.from(inputView.readFormationChoice(teamColor)));
        } catch (RuntimeException exception) {
            outputView.printError("상차림 입력이 올바르지 않습니다.");
            return Optional.empty();
        }
    }
}
