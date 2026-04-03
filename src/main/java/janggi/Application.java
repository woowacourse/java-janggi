package janggi;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.StandardDBConnection;
import janggi.controller.JanggiController;
import janggi.repository.BoardCellRepositoryImpl;
import janggi.repository.GameRepositoryImpl;
import janggi.service.BoardService;
import janggi.service.GameService;

public class Application {

    public static void main(String[] args) {
        final DBConnection dbConnection = new StandardDBConnection();
        final DBTableInitializer dbTableInitializer = new DBTableInitializer(dbConnection);
        dbConnection.init();
        dbTableInitializer.init();

        final GameService gameService =
            new GameService(new GameRepositoryImpl(dbConnection));
        final BoardService boardService = new BoardService(new GameRepositoryImpl(dbConnection),
            new BoardCellRepositoryImpl(dbConnection));
        final JanggiController janggiController =
            new JanggiController(gameService, boardService);
        janggiController.run();
    }
}
