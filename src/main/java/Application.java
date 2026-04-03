import infrastructure.DatabaseManager;
import infrastructure.repository.GameRoomRepository;
import infrastructure.repository.H2GameRoomRepository;
import io.GameConsole;

public class Application {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.initSchema();

        GameRoomRepository gameRoomRepository = new H2GameRoomRepository(databaseManager);
        GameConsole gameConsole = new GameConsole(gameRoomRepository);
        gameConsole.run();
    }
}
