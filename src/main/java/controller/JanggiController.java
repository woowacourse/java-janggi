package controller;

import view.OutputView;

public final class JanggiController {

    private final OutputView outputView;

    public JanggiController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void run() {
        outputView.printGameStart();
    }
}
