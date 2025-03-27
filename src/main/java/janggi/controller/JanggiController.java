package janggi.controller;

import janggi.JanggiGame;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        JanggiGame janggiGame = new JanggiGame();
        outputView.printBoard(janggiGame.getBoard());
    }
}
