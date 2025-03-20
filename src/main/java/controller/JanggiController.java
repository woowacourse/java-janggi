package controller;

import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final JanggiService service = new JanggiService();

    public void startGame() {
        service.startGame();
        outputView.startGame();
        outputView.board(service.getBoard());
    }

    public boolean playTurn() {
        outputView.turn(service.currentTurn());

        var response = inputView.command();
        if(response.abstain()){
            return false;
        }

        service.move(response.source(), response.destination());
        outputView.board(service.getBoard());

        return service.isPlaying();
    }

    public void nextTurn() {
        service.nextTurn();
    }

    public void endGame() {
        outputView.result(service.getWinner());
    }
}
