package janggi;

import janggi.controller.JanggiController;
import janggi.infra.JdbcPieceRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        final JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new JdbcPieceRepository()
        );
        janggiController.run();
    }
}
