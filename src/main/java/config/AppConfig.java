package config;

import controller.JanggiController;
import exception.GameExceptionHandler;
import infra.dao.CurrentPiecePositionDao;
import infra.dao.FormationDao;
import infra.dao.GameDao;
import infra.dao.MoveEventDao;
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
        return new JdbcGameRepository(gameDao(), currentPiecePositionDao(), formationDao(), moveEventDao());
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

    public CurrentPiecePositionDao currentPiecePositionDao() {
        return new CurrentPiecePositionDao(JdbcConfig.getInstance());
    }

    public FormationDao formationDao() {
        return new FormationDao(JdbcConfig.getInstance());
    }

    public MoveEventDao moveEventDao() {
        return new MoveEventDao(JdbcConfig.getInstance());
    }
}
