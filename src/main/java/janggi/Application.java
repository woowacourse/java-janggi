package janggi;

import janggi.controller.GameController;
import janggi.controller.JanggiController;
import janggi.controller.MoveController;
import janggi.repository.JdbcDataSource;
import janggi.repository.MoveRepository;
import janggi.repository.h2.H2DataSource;
import janggi.repository.h2.H2GameRepository;
import janggi.repository.h2.H2MoveRepository;
import janggi.service.GameService;
import janggi.service.MoveService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView(System.in);
        OutputView outputView = new OutputView();
        JdbcDataSource dataSource = new H2DataSource();
        MoveRepository moveRepository = new H2MoveRepository(dataSource);
        GameService gameService = new GameService(new H2GameRepository(dataSource), moveRepository);
        MoveService moveService = new MoveService(moveRepository);
        MoveController moveController = new MoveController(inputView, outputView, moveService);
        GameController gameController = new GameController(inputView, outputView, gameService, moveController);

        JanggiController janggiController = new JanggiController(inputView, outputView, gameController);
        janggiController.run();
    }


}
