package janggi;

import janggi.common.Constants;
import janggi.dao.GameRoomDAO;
import janggi.dao.PieceDAO;
import janggi.manager.DatabaseManager;
import janggi.manager.JanggiGame;
import janggi.service.JanggiService;
import janggi.view.Viewer;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManager(Constants.SERVER, Constants.DATABASE);
            databaseManager.createTableIfNotExist();

            Viewer viewer = new Viewer();
            JanggiService janggiService = new JanggiService(new GameRoomDAO(databaseManager),
                    new PieceDAO(databaseManager));

            JanggiGame janggiGame = new JanggiGame(viewer, janggiService);

            janggiGame.start();
        } catch (SQLException e) {
            System.err.println("에러가 발생하였습니다.");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
