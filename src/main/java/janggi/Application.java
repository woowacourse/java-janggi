package janggi;

import janggi.controller.Controller;
import janggi.domain.piece.gererator.DefaultChoPieceGenerator;
import janggi.domain.piece.gererator.DefaultHanPieceGenerator;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        Controller controller = new Controller(
                new InputView(),
                new OutputView(),
                new DefaultHanPieceGenerator(),
                new DefaultChoPieceGenerator()
        );

        controller.run();
    }
}
