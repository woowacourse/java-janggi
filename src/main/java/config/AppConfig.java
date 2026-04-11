package config;

import controller.JanggiController;
import exception.GameExceptionHandler;
import infra.dao.CurrentPiecePositionDao;
import infra.dao.FormationDao;
import infra.dao.GameDao;
import infra.repository.GameRepository;
import infra.repository.JdbcGameRepository;
import view.InputView;
import view.OutputView;

public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public JanggiController janggiController() {
        return new JanggiController(gameExceptionHandler(), inputView(), outputView(), gameRepository());
    }

    public GameRepository gameRepository() {
        return new JdbcGameRepository(gameDao(), boardDao(), formationDao());
    }

    public GameExceptionHandler gameExceptionHandler() {
        return new GameExceptionHandler(outputView());
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public GameDao gameDao() {
        return new GameDao(JdbcConfig.getInstance());
    }

    public CurrentPiecePositionDao boardDao() {
        return new CurrentPiecePositionDao(JdbcConfig.getInstance());
    }

    public FormationDao formationDao() {
        return new FormationDao(JdbcConfig.getInstance());
    }
}
