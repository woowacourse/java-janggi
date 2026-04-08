package janggi;

import janggi.config.DataSourceConfig;
import janggi.controller.JanggiController;
import janggi.dao.GameDao;
import janggi.dao.PieceDao;
import janggi.service.JanggiService;
import javax.sql.DataSource;

public class Application {

    public static void main(String[] args) {
        DataSource dataSource = DataSourceConfig.createDataSource();

        GameDao gameDao = new GameDao(dataSource);
        PieceDao pieceDao = new PieceDao(dataSource);
        JanggiService janggiService = new JanggiService(gameDao, pieceDao);
        JanggiController janggiController = new JanggiController(janggiService);

        janggiController.run();
    }
}
