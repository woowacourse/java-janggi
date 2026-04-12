import application.GamePersistenceService;
import application.GameService;
import controller.GameController;
import infra.jdbc.JdbcConnectionManager;
import infra.jdbc.JdbcGameRepository;
import infra.jdbc.SchemaInitializer;
import java.time.Clock;
import repository.SavedGameReadMapper;
import repository.SavedGameWriteMapper;
import view.GamePresenter;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JdbcConnectionManager connectionManager = JdbcConnectionManager.defaultConnectionManager();
        new SchemaInitializer(connectionManager).initialize();
        SavedGameWriteMapper writeMapper = new SavedGameWriteMapper(Clock.systemDefaultZone());
        SavedGameReadMapper readMapper = new SavedGameReadMapper();
        JdbcGameRepository gameRepository = new JdbcGameRepository(connectionManager, writeMapper, readMapper);
        GamePersistenceService persistenceService = new GamePersistenceService(gameRepository);
        GameService gameService = new GameService(persistenceService);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GamePresenter gamePresenter = new GamePresenter(outputView);
        GameController gameController = new GameController(inputView, gameService, gamePresenter);
        gameController.run();
    }
}
