package janggi;

import janggi.domain.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        new JanggiController(
                new InputView(),
                new OutputView()
        ).run();
    }

}
