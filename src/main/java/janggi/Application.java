package janggi;

import janggi.controller.Controller;
import janggi.controller.OfflineController;
import janggi.controller.OnlineController;
import janggi.data.DatabaseConnection;
import janggi.data.DatabaseTable;
import janggi.data.dao.BoardDao;
import janggi.data.dao.CampDao;
import janggi.data.dao.PieceDao;
import janggi.data.dao.PieceSymbolDao;
import janggi.view.View;

public final class Application {

    public static void main(String[] args) {
        Controller controller = createController();
        controller.gameStart();
    }

    private static Controller createController() {
        if (DatabaseConnection.isConnected()) {
            DatabaseTable.create();
            return new OnlineController(new View(), new CampDao(), new PieceSymbolDao(), new BoardDao(), new PieceDao());
        }
        return new OfflineController(new View());
    }
}
