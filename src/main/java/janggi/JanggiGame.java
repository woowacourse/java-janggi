package janggi;

import janggi.dao.BoardDao;
import janggi.dao.MySQLDatabaseConnector;
import janggi.dao.TurnDao;
import janggi.manager.GameManager;
import janggi.view.Input;
import janggi.view.Output;

public class JanggiGame {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager(
                new Input(),
                new Output(),
                new BoardDao(new MySQLDatabaseConnector()),
                new TurnDao(new MySQLDatabaseConnector())
        );

        gameManager.play();
    }
}
