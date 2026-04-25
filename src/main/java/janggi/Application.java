package janggi;

import janggi.controller.Controller;
import janggi.repository.GameDao;
import janggi.repository.PieceDao;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        GameDao gameDao = new GameDao();
        PieceDao pieceDao = new PieceDao();

        JanggiService janggiService = new JanggiService(gameDao, pieceDao);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Controller controller = new Controller(inputView, outputView, janggiService);

        controller.run();
    }
}
