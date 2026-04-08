package janggi;

import janggi.controller.JanggiController;
import janggi.database.DatabaseConnection;
import janggi.repository.GameRepository;
import janggi.repository.GimulRepository;
import janggi.repository.jdbc.JdbcGameRepository;
import janggi.repository.jdbc.JdbcGimulRepository;
import janggi.service.JanggiService;
import janggi.service.TransactionManager;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiApplication {

    public static void main(String[] args) {
        DatabaseConnection.initSchema();

        TransactionManager transactionManager = new TransactionManager();
        GameRepository gameRepository = new JdbcGameRepository(transactionManager);
        GimulRepository gimulRepository = new JdbcGimulRepository(transactionManager);
        JanggiService janggiService = new JanggiService(gameRepository, gimulRepository, transactionManager);

        JanggiController controller = new JanggiController(
                janggiService,
                new OutputView(),
                new InputView()
        );

        controller.run();
    }
}
