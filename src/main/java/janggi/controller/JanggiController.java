package janggi.controller;

import static janggi.controller.ControllerUtil.retry;

import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.EnumMap;
import java.util.Map;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Map<GameSelect, Runnable> selectAction = new EnumMap<>(GameSelect.class);
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
        GameSelect gameSelect = retry(inputView::readGameSelect, outputView);
        while (GameSelect.QUIT != gameSelect) {
            GameSelect finalGameSelect = gameSelect;
            retry(() -> runSelected(finalGameSelect), outputView);
            outputView.printSelectGame();
            gameSelect = inputView.readGameSelect();
        }

    }

    private void runSelected(GameSelect gameSelect) {
        retry(() -> selectAction.get(gameSelect).run(), outputView);
    }

}
