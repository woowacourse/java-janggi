package janggi;

import janggi.controller.GameController;
import janggi.controller.JanggiController;
import janggi.dao.BoardDao;
import janggi.dao.JdbcDataSource;
import janggi.dao.h2.H2BoardDao;
import janggi.dao.h2.H2DataSource;
import janggi.dao.h2.H2GameDao;
import janggi.service.GameService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();
        JdbcDataSource dataSource = new H2DataSource();
        BoardDao boardDao = new H2BoardDao(dataSource);
        GameService gameService = new GameService(new H2GameDao(dataSource), boardDao);

        GameController gameController = new GameController(inputView, outputView, gameService);

        JanggiController janggiController = new JanggiController(inputView, outputView, gameController);
        janggiController.run();
    }


}
