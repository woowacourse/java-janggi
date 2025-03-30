package janggi;

import janggi.controller.ApplicationConfigurer;
import janggi.controller.GameController;
import janggi.service.GameService;
import janggi.view.BoardInitiliazeView;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        ApplicationConfigurer applicationConfigurer = new ApplicationConfigurer(new BoardInitiliazeView());
        GameService gameService = applicationConfigurer.configureGameService();

        GameController gameController =
            new GameController(new InputView(), new OutputView(), gameService);
        gameController.play();
    }
}
