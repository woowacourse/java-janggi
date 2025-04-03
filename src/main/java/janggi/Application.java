package janggi;

import janggi.game.JanggiGame;
import janggi.view.Console;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import repository.connection.H2ConnectManager;
import repository.dao.AttackTurnDAO;
import repository.dao.BoardDAO;
import repository.dao.GameDAO;

public class Application {
    public static void main(String[] args) {
        Console console = new Console(new InputView(), new OutputView(), new InputParser());
        H2ConnectManager h2ConnectManager = new H2ConnectManager();
        GameDAO gameDAO = new GameDAO(new BoardDAO(h2ConnectManager), new AttackTurnDAO(h2ConnectManager));

        JanggiGame janggiGame = new JanggiGame(console, gameDAO);
        janggiGame.play();
    }
}
