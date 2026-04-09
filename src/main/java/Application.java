import config.DbConfig;
import controller.JanggiController;
import repository.GameRepository;
import repository.jdbc.ConnectionProvider;
import repository.jdbc.JdbcGameRepository;
import service.GamePersistenceService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        DbConfig dbConfig = DbConfig.load();
        ConnectionProvider connectionProvider = new ConnectionProvider(dbConfig);

        GameRepository gameRepository = new JdbcGameRepository(connectionProvider);
        GamePersistenceService gamePersistenceService = new GamePersistenceService(gameRepository);

        JanggiController janggiController =
                new JanggiController(new InputView(), new OutputView(), gamePersistenceService);
        janggiController.start();
    }
}
