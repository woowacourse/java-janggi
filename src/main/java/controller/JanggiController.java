package controller;

import java.util.function.Supplier;

import constant.JanggiConstant;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final JanggiService service = new JanggiService();

    public int startGame() {
        return process(() -> {
            outputView.startGame();
            Integer gameId = inputView.selectGameRoom(service.gameList());
            if (gameId == JanggiConstant.NEW_GAME_ROOM_ID) {
                gameId = service.newGame(inputView.gameName());
                setTableSetting(gameId);
            }
            return gameId;
        });
    }

    private void setTableSetting(int gameId) {
        process(() -> {
            teamTableSetting(gameId);
            service.nextTurn(gameId);
            teamTableSetting(gameId);
            service.nextTurn(gameId);
        });
    }

    private void teamTableSetting(int gameId) {
        var response = inputView.tableSetting(service.currentTurn(gameId));
        service.tableSettingForCurrentTurn(gameId, response.tableSetting());
    }

    public void playTurn(int gameId) {
        process(() -> {
            outputView.board(service.getBoard(gameId));
            outputView.turn(service.currentTurn(gameId));
            var response = inputView.command();
            if (response.abstain()) {
                service.abstain(gameId);
                return;
            }
            service.move(gameId, response.source(), response.destination());
        });
    }

    public boolean isPlaying(int gameId) {
        return process(() -> service.isPlaying(gameId));
    }

    public void nextTurn(int gameId) {
        process(() -> service.nextTurn(gameId));
    }

    public void endGame(int gameId) {
        process(() -> {
            outputView.result(service.getWinner(gameId));
            outputView.finalScore(service.finalScore(gameId));
            service.endGame(gameId);
        });
    }

    private void process(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.retry(e);
            process(action);
        }
    }

    private <T> T process(Supplier<T> action) {
        try {
            return action.get();
        } catch (IllegalArgumentException e) {
            outputView.retry(e);
            return process(action);
        }
    }
}
