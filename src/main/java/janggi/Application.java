package janggi;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.PropertiesReader;
import janggi.config.StandardDBConnection;
import janggi.controller.JanggiController;
import janggi.dto.H2DBPropertiesDto;
import janggi.infrastructure.repository.BoardCellRepositoryImpl;
import janggi.infrastructure.repository.GameRepositoryImpl;
import janggi.service.BoardService;
import janggi.service.GameService;

public class Application {

    public static void main(String[] args) {
        final H2DBPropertiesDto h2DBPropertiesDto = H2DBPropertiesDto.of(
            PropertiesReader.read("application.properties"));
        final DBConnection dbConnection = new StandardDBConnection(h2DBPropertiesDto);
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
