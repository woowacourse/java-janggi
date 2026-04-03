package janggi.config;

import janggi.controller.JanggiController;
import janggi.controller.JanggiService;
import janggi.repository.GameRepository;
import janggi.repository.JdbcGameRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {

    private JanggiController controller;
    private JanggiService service;
    private GameRepository gameRepository;
    private InputView inputView;
    private OutputView outputView;

    public JanggiController controller() {
        if (controller == null) {
            controller = new JanggiController(service(), inputView(), outputView());
        }
        return controller;
    }

    private JanggiService service() {
        if (service == null) {
            service = new JanggiService(gameRepository());
        }
        return service;
    }

    private GameRepository gameRepository() {
        if (gameRepository == null) {
            gameRepository = new JdbcGameRepository();
        }
        return gameRepository;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    private OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }

}
