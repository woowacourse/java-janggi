package janggi;

import janggi.dao.ConnectionManager;
import janggi.dao.JanggiDatabaseManager;
import janggi.dao.PieceDao;
import janggi.dao.PieceTypeDao;
import janggi.dao.TeamDao;
import janggi.dao.utils.JanggiMapper;
import janggi.view.InputView;
import janggi.view.ResultView;

public class JanggiApplication {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final ResultView resultView = new ResultView();

        final ConnectionManager connectionManager = new ConnectionManager();
        final JanggiMapper janggiMapper = new JanggiMapper();
        final PieceDao pieceDao = new PieceDao(connectionManager);
        final PieceTypeDao pieceTypeDao = new PieceTypeDao(connectionManager);
        final TeamDao teamDao = new TeamDao(connectionManager);
        final JanggiDatabaseManager janggiDatabaseManager = new JanggiDatabaseManager(pieceDao, pieceTypeDao,
                teamDao, janggiMapper);

        JanggiConsole janggiConsole = new JanggiConsole(inputView, resultView, janggiDatabaseManager);
        janggiConsole.start();
    }
}
