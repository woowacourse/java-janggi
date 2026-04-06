package janggi;

import janggi.controller.JanggiController;
import janggi.repository.GameRepository;
import janggi.repository.JdbcContext;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();

        JdbcContext jdbcContext = new JdbcContext();
        GameRepository gameRepository = new GameRepository(jdbcContext);
        GameService gameService = new GameService(gameRepository);

        JanggiController janggiController = new JanggiController(inputView, outputView, gameService);
        janggiController.run();
    }
}
