package console;

import application.JanggiService;
import console.util.ExceptionHandler;
import console.view.GameMenuCommand;
import console.view.InputView;
import console.view.OutputView;
import java.util.Map;
import java.util.Optional;

public class JanggiConsoleRunner {

    private final Map<GameMenuCommand, JanggiController> controllers;

    public JanggiConsoleRunner(JanggiService janggiService) {
        final JanggiExecutor janggiExecutor = new JanggiExecutor(janggiService);
        controllers = Map.of(
                GameMenuCommand.CONTINUE, new ContinueController(janggiExecutor, janggiService),
                GameMenuCommand.NEW_GAME, new NewGameController(janggiExecutor, janggiService)
        );
    }

    public void run() {
        GameMenuCommand gameMenu;
        do {
            gameMenu = ExceptionHandler.handleRetry(InputView::readGameMenu, OutputView::displayError);
            Optional.ofNullable(controllers.get(gameMenu))
                    .ifPresent(JanggiController::run);
        } while (gameMenu != GameMenuCommand.END);
    }
}
