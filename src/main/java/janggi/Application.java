package janggi;

import janggi.board.dao.utils.DatabaseUtils;
import janggi.database.DBConnector;
import janggi.database.DBInitializer;
import janggi.database.MySQLDBConnector;
import janggi.manager.JanggiGame;
import janggi.service.JanggiGameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final DBConnector connector = new MySQLDBConnector();
        final DatabaseUtils databaseUtils = new DatabaseUtils(connector);
        final DBInitializer dbInitializer = new DBInitializer(connector);
        dbInitializer.createTables();
        final JanggiGame janggiGame = new JanggiGame(inputView, outputView, new JanggiGameService(databaseUtils));
        janggiGame.start();
    }
}
