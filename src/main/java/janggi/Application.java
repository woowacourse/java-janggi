package janggi;

import janggi.controller.Controller;
import janggi.repository.JanggiRepository;
import janggi.repository.JdbcJanggiRepository;
import janggi.service.JanggiService;
import janggi.util.DBConnectionManager;
import janggi.util.DatabaseInitializer;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {

        Connection conn = DBConnectionManager.getConnection();
        DatabaseInitializer.initialize(conn);

        JanggiRepository janggiRepository = new JdbcJanggiRepository();
        JanggiService service = new JanggiService(janggiRepository);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Controller controller = new Controller(inputView, outputView, service);

        controller.run();
    }
}
