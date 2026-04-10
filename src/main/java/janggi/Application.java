package janggi;

import janggi.controller.GameController;
import janggi.controller.JanggiController;
import janggi.controller.MoveController;
import janggi.dao.JdbcDataSource;
import janggi.dao.MoveDao;
import janggi.dao.h2.H2DataSource;
import janggi.dao.h2.H2GameDao;
import janggi.dao.h2.H2MoveDao;
import janggi.service.GameService;
import janggi.service.MoveService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();
        JdbcDataSource dataSource = new H2DataSource();
        MoveDao moveDao = new H2MoveDao(dataSource);
        GameService gameService = new GameService(new H2GameDao(dataSource), moveDao);
        MoveService moveService = new MoveService(moveDao);
        MoveController moveController = new MoveController(inputView, outputView, moveService);
        GameController gameController = new GameController(inputView, outputView, gameService, moveController);

        JanggiController janggiController = new JanggiController(inputView, outputView, gameController);
        janggiController.run();
    }


}
