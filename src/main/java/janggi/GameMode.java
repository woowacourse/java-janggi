package janggi;

import janggi.controller.Controller;
import janggi.controller.OfflineController;
import janggi.controller.OnlineController;
import janggi.data.DatabaseTable;
import janggi.data.dao.BoardDao;
import janggi.data.dao.CampDao;
import janggi.data.dao.PieceDao;
import janggi.data.dao.PieceSymbolDao;
import janggi.view.View;

enum GameMode {

    OFFLINE,
    ONLINE,
    ;

    public static GameMode from(boolean isOnline) {
        if (isOnline) {
            DatabaseTable.create();
            return ONLINE;
        }
        return OFFLINE;
    }

    public Controller createController() {
        return switch (this) {
            case ONLINE -> new OnlineController(
                    new View(),
                    new CampDao(),
                    new PieceSymbolDao(),
                    new BoardDao(),
                    new PieceDao());
            case OFFLINE -> new OfflineController(new View());
        };
    }
}
