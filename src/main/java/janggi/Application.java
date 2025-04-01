package janggi;

import janggi.controller.Controller;
import janggi.domain.piece.generator.ChoPieceGenerator;
import janggi.domain.piece.generator.HanPieceGenerator;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        Controller controller = new Controller(
            new InputView(),
            new OutputView(),
            new HanPieceGenerator(),
            new ChoPieceGenerator()
        );

        controller.run();
    }
}
