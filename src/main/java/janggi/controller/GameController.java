package janggi.controller;

import janggi.domain.Team;
import janggi.service.GameService;
import janggi.service.OnlineGameService;
import janggi.view.InputView;
import janggi.view.InputView.UserInput;
import janggi.view.OutputView;
import java.util.function.Supplier;

public class GameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final GameService service;

    public GameController(final InputView inputView, final OutputView outputView,
        final GameService service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.service = service;
    }

    public void play() {
        retryingWhileCondition(() -> {
            outputView.printBoard(service.allPieces());

            Team currentTurn = service.currentTurn();
            UserInput userInput = inputView.readMoveOrder(currentTurn);
            if (userInput.wantsToQuit) {
                quit();
                return false;
            }

            service.movePiece(userInput.departure, userInput.arrival);
            if (service.isGameOver()) {
                service.clearGame();
                gameOver();
                return false;
            }
            return true;
        });
    }

    private void quit() {
        outputView.printScore(service.scoreTeams());
        outputView.printSaveAndQuit();
    }

    private void gameOver() {
        outputView.printScore(service.scoreTeams());
        outputView.printFinished(service.higherScoreTeam());
    }

    private void retryingWhileCondition(Supplier<Boolean> keepRunningSupplier) {
        boolean keepRunning = true;
        do {
            try {
                keepRunning = keepRunningSupplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printException(e);
            }
        } while (keepRunning);
    }
}
