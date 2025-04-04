package janggi;

import janggi.domain.board.dao.JanggiBoardDAO;
import janggi.domain.board.dao.JanggiBoardDAOImpl;
import janggi.domain.board.dao.TeamDAO;
import janggi.domain.board.dao.TeamDAOImpl;
import janggi.domain.board.dao.TurnDAO;
import janggi.domain.board.dao.TurnDAOImpl;
import janggi.database.utils.DatabaseUtils;
import janggi.database.DBConnector;
import janggi.database.DBInitializer;
import janggi.database.MySQLDBConnector;
import janggi.domain.manager.JanggiGame;
import janggi.domain.service.JanggiGameService;
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
