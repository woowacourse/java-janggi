import database.ConnectionManager;
import database.DatabaseConfig;
import database.DatabaseInitializer;
import database.H2GameRepository;
import domain.game.GameRepository;

public class Main {

    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        ConnectionManager connectionManager = new ConnectionManager(databaseConfig);
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionManager);
        databaseInitializer.initialize();
        GameRepository gameRepository = new H2GameRepository(connectionManager);

        new GameRunner(gameRepository).run();
    }
}
