package console;

import static console.util.ExceptionHandler.handle;
import static console.util.ExceptionHandler.handleRetry;

import application.JanggiService;
import console.view.InputView;
import console.view.OutputView;
import java.util.Map;
import model.GameStatus;

public class ContinueController implements JanggiController {

    private final JanggiExecutor janggiExecutor;
    private final JanggiService janggiService;

    public ContinueController(JanggiExecutor janggiExecutor, JanggiService janggiService) {
        this.janggiExecutor = janggiExecutor;
        this.janggiService = janggiService;
    }

    @Override
    public void run() {
        Map<Long, GameStatus> gameStatusById = janggiService.collectGameStatus();
        if (gameStatusById.isEmpty()) {
            OutputView.displayNoGame();
            return;
        }

        Long gameId = handleRetry(() -> InputView.readPlayGameId(gameStatusById), OutputView::displayError);
        handle(() -> janggiExecutor.playGame(gameId), OutputView::displayError);
    }
}
