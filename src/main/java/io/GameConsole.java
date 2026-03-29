package io;

import domain.game.JanggiGame;
import java.util.function.Supplier;

public class GameConsole {
    private final OutputView outputView;
    private final InputView inputView;
    private final JanggiGame janggiGame;

    public GameConsole() {
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.janggiGame = new JanggiGame();
    }

    public void run() {
        while (true) {
            retryUntilSuccess(() -> {
                janggiGame.displayRequestCommand(outputView);
                janggiGame.processCommand(inputView.readCommand());
                return null;
            });
        }
    }

    private <T> T retryUntilSuccess(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e.getMessage());
            return retryUntilSuccess(action);
        }
    }
}
