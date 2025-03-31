package janggi;

import janggi.dao.JanggiDao;
import janggi.manager.ConnectionManager;
import janggi.manager.JanggiDatabaseManager;
import janggi.manager.JanggiMapper;
import janggi.view.InputView;
import janggi.view.ResultView;

public class JanggiApplication {
    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final ResultView resultView = new ResultView();

        final ConnectionManager connectionManager = new ConnectionManager();
        final JanggiMapper janggiMapper = new JanggiMapper();
        final JanggiDao janggiDao = new JanggiDao(connectionManager);
        final JanggiDatabaseManager janggiDatabaseManager = new JanggiDatabaseManager(janggiDao, janggiMapper);

        JanggiConsole janggiConsole = new JanggiConsole(inputView, resultView, janggiDatabaseManager);
        janggiConsole.start();
    }
}
