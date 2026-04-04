package janggi;

import janggi.controller.JanggiController;
import janggi.repository.GameRepository;
import janggi.repository.JdbcContext;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        JdbcContext jdbcContext = new JdbcContext();
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();
        GameRepository gameRepository = new GameRepository();

        JanggiController janggiController = new JanggiController(inputView, outputView, gameRepository);
        janggiController.run();
    }
}
