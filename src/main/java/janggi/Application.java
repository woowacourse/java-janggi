package janggi;

import janggi.persistence.DatabaseInitializer;
import janggi.persistence.repository.JanggiGameRepository;
import janggi.persistence.repository.JdbcJanggiGameRepository;
import janggi.service.JanggiService;
import janggi.view.input.ConsoleInputView;
import janggi.view.input.InputView;
import janggi.view.output.ConsoleOutputView;
import janggi.view.output.OutputView;

public class Application {

    public static void main(String[] args) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.initialize();
        JanggiGameRepository janggiGameRepository = new JdbcJanggiGameRepository();
        JanggiService janggiService = new JanggiService(janggiGameRepository);
        InputView inputView = new ConsoleInputView();
        OutputView outputView = new ConsoleOutputView();
        JanggiRunner janggiRunner = new JanggiRunner(inputView, outputView, janggiService);
        janggiRunner.execute();
    }
}
