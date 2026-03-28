package janggi.config;

import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {
    public JanggiController janggiController() {
        return new JanggiController(new InputView(), new OutputView());
    }
}
