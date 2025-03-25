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
        });
    }

    public void setTableSetting() {
        process(() -> {
            teamTableSetting();
            service.nextTurn();
            teamTableSetting();
            service.nextTurn();
            outputView.board(service.getBoard());
        });
    }

    private void teamTableSetting() {
        var response = inputView.tableSetting(service.currentTurn());
        service.tableSettingForCurrentTurn(response.tableSetting());
    }

    public void playTurn() {
        process(() -> {
            outputView.turn(service.currentTurn());
            var response = inputView.command();
            if (response.abstain()) {
                service.abstain();
                return;
            }
            service.move(response.source(), response.destination());
            outputView.board(service.getBoard());
        });
    }

    public boolean isPlaying() {
        return process(service::isPlaying);
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
            process(action);
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
