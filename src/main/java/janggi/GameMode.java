package janggi;

import janggi.controller.Controller;
import janggi.controller.OfflineController;
import janggi.controller.OnlineController;
import janggi.data.DatabaseTable;
import janggi.data.dao.BoardDao;
import janggi.data.dao.CampDao;
import janggi.data.dao.PieceDao;
import janggi.data.dao.PieceSymbolDao;
import janggi.data.dao.mysql.MySqlBoardDao;
import janggi.data.dao.mysql.MySqlCampDao;
import janggi.data.dao.mysql.MySqlPieceDao;
import janggi.data.dao.mysql.MySqlPieceSymbolDao;
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
        CampDao campDao = new MySqlCampDao();
        PieceSymbolDao pieceSymbolDao = new MySqlPieceSymbolDao();
        BoardDao boardDao = new MySqlBoardDao(campDao);
        PieceDao pieceDao = new MySqlPieceDao(campDao, boardDao, pieceSymbolDao);
        return switch (this) {
            case ONLINE -> new OnlineController(new View(), campDao, pieceSymbolDao, boardDao, pieceDao);
            case OFFLINE -> new OfflineController(new View());
        };
    }
}
