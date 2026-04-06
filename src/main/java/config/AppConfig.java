package config;

import controller.JanggiController;
import exception.GameExceptionHandler;
import infra.dao.BoardDao;
import infra.dao.TurnDao;
import infra.repository.BoardRepository;
import infra.repository.JdbcBoardRepository;
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

    public BoardRepository gameRepository() {
        return new JdbcBoardRepository(boardDao(), turnDao());
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

    public BoardDao boardDao() {
        return new BoardDao(JdbcConfig.getInstance());
    }

    public TurnDao turnDao() {
        return new TurnDao(JdbcConfig.getInstance());
    }
}
