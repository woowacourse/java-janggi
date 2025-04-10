package janggi;

import janggi.dao.BoardDao;
import janggi.dao.GameRoomDao;
import janggi.dao.MySQLDatabaseConnector;
import janggi.game.GameManager;
import janggi.view.Input;
import janggi.view.Output;

public class Application {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager(
                new Input(),
                new Output(),
                new BoardDao(new MySQLDatabaseConnector()),
                new GameRoomDao(new MySQLDatabaseConnector())
        );

        gameManager.progress();
    }
}
