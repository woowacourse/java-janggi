package janggi;

import janggi.dao.JanggiDao;
import janggi.manager.ConnectionManager;
import janggi.manager.JanggiManager;
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
        final JanggiManager janggiManager = new JanggiManager(janggiDao, janggiMapper);

        JanggiConsole janggiConsole = new JanggiConsole(inputView, resultView, janggiManager);
        janggiConsole.start();
    }
}
