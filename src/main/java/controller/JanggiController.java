package controller;

import java.util.function.BooleanSupplier;

import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final JanggiService service = new JanggiService();

    public void startGame() {
        process(() -> {
            service.startGame();
            outputView.startGame();
            outputView.board(service.getBoard());
        });
    }

    public boolean playTurn() {
        return process(() -> {
            outputView.turn(service.currentTurn());

            var response = inputView.command();
            if (response.abstain()) {
                return false;
            }

            service.move(response.source(), response.destination());
            outputView.board(service.getBoard());

            return service.isPlaying();
        });
    }

    public void nextTurn() {
        process(service::nextTurn);
    }

    public void endGame() {
        process(() -> outputView.result(service.getWinner()));
    }

    private void process(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.retry(e);
            action.run();
        }
    }

    private boolean process(BooleanSupplier action) {
        try {
            return action.getAsBoolean();
        } catch (IllegalArgumentException e) {
            outputView.retry(e);
            return process(action);
        }
    }
}
