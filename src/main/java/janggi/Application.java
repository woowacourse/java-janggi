package janggi;

import janggi.config.DBConnection;
import janggi.config.DBTableInitializer;
import janggi.config.PropertiesReader;
import janggi.config.StandardDBConnection;
import janggi.controller.JanggiController;
import janggi.dto.H2DBPropertiesDto;
import janggi.infrastructure.repository.BoardCellRepository;
import janggi.infrastructure.repository.BoardCellRepositoryImpl;
import janggi.infrastructure.repository.GameRepository;
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

        final GameRepository gameRepository = new GameRepositoryImpl(dbConnection);
        final BoardCellRepository boardCellRepository = new BoardCellRepositoryImpl(dbConnection);
        final GameService gameService = new GameService(gameRepository, boardCellRepository);
        final BoardService boardService = new BoardService(boardCellRepository);
        final JanggiController janggiController =
            new JanggiController(gameService, boardService);
        janggiController.run();
    }
}
