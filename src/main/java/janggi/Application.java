package janggi;

import janggi.controller.JanggiController;
import janggi.db.DatabaseConfig;
import janggi.db.DatabaseConnection;
import janggi.db.DatabaseInitializer;
import janggi.db.dao.JdbcGameDao;
import janggi.db.dao.JdbcPieceDao;
import janggi.db.repository.GameRepository;
import janggi.db.repository.JdbcGameRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        DatabaseConfig config = DatabaseConfig.load();
        DatabaseConnection connection = new DatabaseConnection(config);
        DatabaseInitializer.initialize(config);

        JdbcGameDao gameDao = new JdbcGameDao(connection);
        JdbcPieceDao pieceDao = new JdbcPieceDao(connection);
        GameRepository gameRepository = new JdbcGameRepository(gameDao, pieceDao);

        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), gameRepository);

        janggiController.start();
    }
}
