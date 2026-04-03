import infrastructure.DatabaseManager;
import infrastructure.repository.GameRepository;
import infrastructure.repository.GameRoomRepository;
import infrastructure.repository.H2GameRepository;
import infrastructure.repository.H2GameRoomRepository;
import io.GameConsole;

public class Application {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.initSchema();

        GameRoomRepository gameRoomRepository = new H2GameRoomRepository(databaseManager);
        GameRepository gameRepository = new H2GameRepository(databaseManager);
        GameConsole gameConsole = new GameConsole(gameRoomRepository, gameRepository);
        gameConsole.run();
    }
}
