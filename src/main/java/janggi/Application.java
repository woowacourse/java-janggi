package janggi;

import janggi.controller.JanggiController;
import janggi.database.MySQLDatabaseConnection;
import janggi.database.QueryProcessor;
import janggi.database.dao.PieceDao;
import janggi.database.dao.TurnDao;
import janggi.repository.DefaultPieceRepository;
import janggi.repository.DefaultTurnRepository;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(final String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final GameService gameService = new GameService(new DefaultPieceRepository(new PieceDao(new QueryProcessor(
                MySQLDatabaseConnection.getInstance()))),
                new DefaultTurnRepository(new TurnDao(new QueryProcessor(MySQLDatabaseConnection.getInstance()))));
        final JanggiController janggiController = new JanggiController(inputView, outputView, gameService);
        janggiController.run();
    }
}
