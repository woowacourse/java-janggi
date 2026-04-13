package io;

import application.GameSession;
import application.GameSessionService;
import java.util.Optional;

public class GameConsole {
    private final GameSessionService gameSessionService;
    private final OutputView outputView;
    private final InputView inputView;

    public GameConsole(
            GameSessionService gameSessionService,
            OutputView outputView,
            InputView inputView
    ) {
        this.gameSessionService = gameSessionService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        GameSession gameSession = initializeGameSession();
        run(gameSession);
        outputView.printGameResult(gameSession.game().getGameResult());
    }

    private GameSession initializeGameSession() {
        Optional<GameSession> storedGameSession = gameSessionService.findInProgress();
        if (storedGameSession.isEmpty()) {
            return gameSessionService.start();
        }
        return chooseGameSession(storedGameSession.get());
    }

    private GameSession chooseGameSession(GameSession storedGameSession) {
        try {
            return selectGameSession(storedGameSession);
        } catch (IllegalArgumentException exception) {
            outputView.printErrorMessage(exception.getMessage());
            return chooseGameSession(storedGameSession);
        }
    }

    private GameSession selectGameSession(GameSession storedGameSession) {
        outputView.printRestoreGamePrompt();
        if (inputView.readRestoreAnswer()) {
            return storedGameSession;
        }
        return gameSessionService.abandonAndStart(storedGameSession.id());
    }

    private void run(GameSession gameSession) {
        while (!gameSession.game().isFinishPhase()) {
            retryUntilSuccess(() -> executeTurn(gameSession));
        }
    }

    private void executeTurn(GameSession gameSession) {
        displayRequestCommand(gameSession);
        gameSessionService.execute(gameSession, inputView.readRawCommand());
    }

    private void displayRequestCommand(GameSession gameSession) {
        if (gameSession.game().isReadyPhase()) {
            outputView.printSetupTable(gameSession.game().getTurn());
        }
        if (gameSession.game().isPlayingPhase()) {
            outputView.printBoard(gameSession.game().getBoard(), gameSession.game().getTurn());
            outputView.printPieceMovement(gameSession.game().getTurn());
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
