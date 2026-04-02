package janggi.config;

import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {

    private JanggiController controller;
    private InputView inputView;
    private OutputView outputView;

    public JanggiController controller() {
        if (controller == null) {
            controller = new JanggiController(inputView(), outputView());
        }
        return controller;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    private OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

}
