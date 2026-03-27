package janggi;

import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {

        JanggiController controller = new JanggiController(
                new OutputView(),
                new InputView()
        );

        controller.run();
    }
}
