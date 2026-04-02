package io;

import domain.game.JanggiGame;

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
                displayRequestCommand();
                janggiGame.processCommand(inputView.readCommand());
            });
        }
    }

    public void displayRequestCommand() {
        if (janggiGame.isReadyPhase()) {
            outputView.printSetupTable(janggiGame.getTurn());
        }
        if (janggiGame.isPlayingPhase()) {
            outputView.printBoard(janggiGame.getBoard(), janggiGame.getTurn());
            outputView.printPieceMovement(janggiGame.getTurn());
        }
    }


    private void retryUntilSuccess(Runnable action) {
        boolean isSuccess = false;

        while (!isSuccess) {
            isSuccess = attempt(action);
        }
    }

    private boolean attempt(Runnable action) {
        try {
            action.run();
            return true;
        } catch (IllegalArgumentException exception) {
            outputView.printErrorMessage(exception.getMessage());
            return false;
        }
    }

}
