import config.DatabaseConfig;
import database.H2ConsoleStarter;
import database.InitTable;
import java.sql.Connection;
import repository.GameRepository;

public class Application {
    public static void main(String[] args) {
        H2ConsoleStarter.start();
        Connection connection = DatabaseConfig.createConnection();
        InitTable.schemaInit(connection);

        GameRepository gameRepository = new GameRepository(connection);
        GameController gameController = new GameController(gameRepository);
        gameController.start();
    }
}
