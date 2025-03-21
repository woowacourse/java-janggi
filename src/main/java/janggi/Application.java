package janggi;

import janggi.controller.Controller;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        Controller controller = new Controller(
                new InputView(),
                new OutputView()
        );

        controller.run();
    }
}
