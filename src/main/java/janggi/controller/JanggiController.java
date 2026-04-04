package janggi.controller;

import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.HashMap;
import java.util.Map;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<GameSelect, Runnable> selectAction = new HashMap<>();
    private final GameController gameController;

    public JanggiController(InputView inputView, OutputView outputView, GameController gameController) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameController = gameController;
        initSelect();
    }

    private void initSelect() {
        selectAction.put(GameSelect.CREATE, gameController::createGame);
        selectAction.put(GameSelect.LOAD, gameController::loadGame);
    }

    public void run() {
        outputView.printSelectGame();
        GameSelect gameSelect = inputView.readGameSelect();
        retry(() -> selectAction.get(gameSelect).run());
    }

    private void retry(Runnable runnable) {
        while (true) {
            try {
                runnable.run();
                break;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
