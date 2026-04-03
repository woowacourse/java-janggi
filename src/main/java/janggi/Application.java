package janggi;

import janggi.controller.JanggiController;
import janggi.repository.h2.H2GameRepository;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();
        GameService gameService = new GameService(new H2GameRepository());
        
        JanggiController janggiController = new JanggiController(inputView, outputView, gameService);
        janggiController.run();
    }
}
