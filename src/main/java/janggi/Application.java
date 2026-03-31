package janggi;

import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(new InputView(), new OutputView());
        janggiController.start();
    }
}
