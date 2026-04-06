package janggi;

import janggi.controller.JanggiController;
import janggi.db.DatabaseConfig;
import janggi.db.DatabaseConnection;
import janggi.db.DatabaseInitializer;
import janggi.db.dao.GameDao;
import janggi.db.dao.JdbcGameDao;
import janggi.db.dao.JdbcPieceDao;
import janggi.db.dao.PieceDao;
import janggi.db.repository.GameRepository;
import janggi.db.repository.JdbcGameRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        DatabaseConfig config = DatabaseConfig.load();
        DatabaseConnection connection = new DatabaseConnection(config);
        DatabaseInitializer.initialize(config);

        GameDao gameDaoTmp = new JdbcGameDao(connection);
        PieceDao pieceDaoTmp = new JdbcPieceDao(connection);
        GameRepository gameRepository = new JdbcGameRepository(gameDaoTmp, pieceDaoTmp);

        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), gameRepository);

        janggiController.start();
    }
}
