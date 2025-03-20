package controller;

import domain.Janggi;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(
        final InputView inputView,
        final OutputView outputView
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Janggi janggi = Janggi.initialize();
        outputView.printBoard(janggi.getPieces(), janggi.getCurrentTeam());
    }
}
