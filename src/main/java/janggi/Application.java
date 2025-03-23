package janggi;

import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(final String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final JanggiController janggiController = new JanggiController(inputView, outputView);
        janggiController.run();
    }
}
