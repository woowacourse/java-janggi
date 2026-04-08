package janggi.config;

import janggi.JanggiRunner;
import janggi.persistence.DatabaseInitializer;
import janggi.persistence.repository.JanggiGameRepository;
import janggi.persistence.repository.JdbcJanggiGameRepository;
import janggi.service.JanggiService;
import janggi.view.input.ConsoleInputView;
import janggi.view.input.InputView;
import janggi.view.output.ConsoleOutputView;
import janggi.view.output.OutputView;

public class AppConfig {

    public DatabaseInitializer databaseInitializer() {
        return new DatabaseInitializer();
    }

    public JanggiRunner janggiRunner() {
        return new JanggiRunner(inputView(), outputView(), janggiService());
    }

    public JanggiService janggiService() {
        return new JanggiService(janggiGameRepository());
    }

    public JanggiGameRepository janggiGameRepository() {
        return new JdbcJanggiGameRepository();
    }

    public InputView inputView() {
        return new ConsoleInputView();
    }

    public OutputView outputView() {
        return new ConsoleOutputView();
    }
}
