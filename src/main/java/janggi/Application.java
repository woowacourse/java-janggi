package janggi;

import janggi.controller.GameController;
import janggi.controller.JanggiController;
import janggi.controller.MoveController;
import janggi.repository.MoveRepository;
import janggi.repository.h2.DatabaseInitializer;
import janggi.repository.h2.H2GameRepository;
import janggi.repository.h2.H2MoveRepository;
import janggi.service.GameService;
import janggi.service.MoveService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();
        MoveRepository moveRepository = new H2MoveRepository();
        GameService gameService = new GameService(new H2GameRepository(), moveRepository);
        MoveService moveService = new MoveService(moveRepository);
        MoveController moveController = new MoveController(inputView, outputView, moveService);
        GameController gameController = new GameController(inputView, outputView, gameService, moveController);
        
        DatabaseInitializer.init();

        JanggiController janggiController = new JanggiController(inputView, outputView, gameController);
        janggiController.run();
    }
}
