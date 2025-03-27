package janggi;

import janggi.common.Constants;
import janggi.dao.PieceDAO;
import janggi.dao.GameRoomDAO;
import janggi.manager.DatabaseManager;
import janggi.manager.JanggiGame;
import janggi.view.Viewer;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManager(Constants.SERVER, Constants.DATABASE);
            databaseManager.createTableIfNotExist();

            Viewer viewer = new Viewer();
            JanggiGame janggiGame = new JanggiGame(viewer, new PieceDAO(databaseManager), new GameRoomDAO(databaseManager));

            janggiGame.start();
        } catch (SQLException e) {
            System.err.println("에러가 발생하였습니다.");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
