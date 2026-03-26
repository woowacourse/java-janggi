package janggi;

import janggi.domain.controller.JanggiController;
import janggi.view.InputView;

public class Application {

    public static void main(String[] args) {
        JanggiController controller = new JanggiController(new InputView());
        controller.run();
    }

}
