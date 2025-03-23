package config;

import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Config {
    private static InputView inputView;
    private static OutputView outputView;
    private static JanggiController janggiController;

    public JanggiController janggiController() {
        if (janggiController == null) {
            return new JanggiController(inputView(), outputView());
        }
        return janggiController;
    }

    private InputView inputView() {
        if (inputView == null) {
            return new InputView();
        }
        return inputView;
    }

    private OutputView outputView() {
        if (outputView == null) {
            return new OutputView();
        }
        return outputView;
    }
}
