package janggi;

import janggi.controller.JanggiController;
import janggi.repository.JdbcBoardRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {

        JanggiController controller = new JanggiController(
                new OutputView(),
                new InputView(),
                new JdbcBoardRepository()
        );

        controller.run();
    }
}
