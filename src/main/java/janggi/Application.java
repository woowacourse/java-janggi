package janggi;

import janggi.game.JanggiGame;
import janggi.view.Console;
import janggi.view.InputParser;
import janggi.view.InputView;
import janggi.view.OutputView;
import repository.connection.MysqlConnectionManager;
import repository.dao.AttackTurnDAO;
import repository.dao.BoardDAO;
import repository.dao.GameDAO;

public class Application {
    public static void main(String[] args) {
        Console console = new Console(new InputView(), new OutputView(), new InputParser());
        MysqlConnectionManager mysqlConnectionManager = new MysqlConnectionManager();
        GameDAO gameDAO = new GameDAO(new BoardDAO(mysqlConnectionManager), new AttackTurnDAO(mysqlConnectionManager));

        JanggiGame janggiGame = new JanggiGame(console, gameDAO);
        janggiGame.play();
    }
}
