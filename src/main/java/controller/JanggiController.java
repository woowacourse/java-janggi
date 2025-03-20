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
}
