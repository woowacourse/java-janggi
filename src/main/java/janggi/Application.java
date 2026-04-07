package janggi;

import janggi.controller.JanggiController;
import janggi.infrastructure.DBInitializer;
import janggi.repository.GameRepository;
import janggi.repository.PieceRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        DBInitializer.initialize();

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameRepository gameRepository = new GameRepository();
        PieceRepository pieceRepository = new PieceRepository();

        JanggiController janggiController = new JanggiController(inputView, outputView, gameRepository,
                pieceRepository);
        janggiController.run();

        janggiController.run();
    }
}
