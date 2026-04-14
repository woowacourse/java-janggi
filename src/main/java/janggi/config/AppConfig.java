package janggi.config;

import janggi.controller.JanggiController;
import janggi.repository.JdbcJanggiRepository;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {
    public JanggiController janggiController() {
        return new JanggiController(new JanggiService(new JdbcJanggiRepository()), new InputView(), new OutputView());
    }
}
