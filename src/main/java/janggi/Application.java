package janggi;

import janggi.controller.Controller;
import janggi.repository.JanggiDao;
import janggi.service.JanggiBoardService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        Controller controller = new Controller(
            new InputView(),
            new OutputView(),
            new JanggiBoardService(new JanggiDao())
        );

        controller.run();
    }
}
