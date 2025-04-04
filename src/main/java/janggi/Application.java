package janggi;

import janggi.board.dao.JanggiBoardDAO;
import janggi.board.dao.JanggiBoardDAOImpl;
import janggi.board.dao.TeamDAO;
import janggi.board.dao.TeamDAOImpl;
import janggi.board.dao.TurnDAO;
import janggi.board.dao.TurnDAOImpl;
import janggi.database.utils.DatabaseUtils;
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
        final JanggiBoardDAO janggiBoardDAO = new JanggiBoardDAOImpl(databaseUtils);
        final TurnDAO turnDAO = new TurnDAOImpl(databaseUtils);
        final TeamDAO teamDAO = new TeamDAOImpl(databaseUtils);
        final DBInitializer dbInitializer = new DBInitializer(connector);
        dbInitializer.createTables();
        final JanggiGame janggiGame = new JanggiGame(inputView, outputView, new JanggiGameService(
                janggiBoardDAO, turnDAO, teamDAO
        ));
        janggiGame.start();
    }
}
