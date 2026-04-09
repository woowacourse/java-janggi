package janggi;

import janggi.controller.JanggiController;
import janggi.repository.GameRepository;
import janggi.repository.JdbcContext;
import janggi.repository.JdbcGameRepository;
import janggi.repository.TransactionManager;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();

        JdbcContext jdbcContext = new JdbcContext();
        TransactionManager transactionManager = new TransactionManager(jdbcContext);
        GameRepository gameRepository = new JdbcGameRepository();
        GameService gameService = new GameService(transactionManager, gameRepository);

        JanggiController janggiController = new JanggiController(inputView, outputView, gameService);
        janggiController.run();
    }
}
