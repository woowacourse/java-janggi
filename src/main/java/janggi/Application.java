package janggi;

import janggi.database.DBConnector;
import janggi.manager.JanggiGame;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.Connection;

public class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        final JanggiGame janggiGame = new JanggiGame(inputView, outputView);
        janggiGame.start();
    }
}
