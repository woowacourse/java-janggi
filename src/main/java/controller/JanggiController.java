package controller;

import domain.board.wing.ChoWings;
import domain.board.wing.HanWings;
import view.InputView;
import view.OutputView;

public final class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        outputView.printGameStart();

        ChoWings choWings = inputView.readChoWings();
        HanWings hanWings = inputView.readHanWings();
    }
}
