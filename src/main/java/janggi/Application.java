package janggi;

import janggi.config.DatabaseConfig;
import janggi.dao.impl.GameRoomDAOImpl;
import janggi.dao.impl.BoardDAOImpl;
import janggi.manager.DatabaseManager;
import janggi.manager.JanggiGame;
import janggi.service.JanggiService;
import janggi.view.Viewer;

public class Application {

    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManager(DatabaseConfig.getServer(),
                    DatabaseConfig.getDatabase());
            databaseManager.createTableIfNotExist();

            Viewer viewer = new Viewer();
            JanggiService janggiService = new JanggiService(new GameRoomDAOImpl(databaseManager),
                    new BoardDAOImpl(databaseManager));

            JanggiGame janggiGame = new JanggiGame(viewer, janggiService);

            janggiGame.start();
        } catch (IllegalArgumentException e) {
            System.out.println("에러가 발생하였습니다!");
        }
    }
}
