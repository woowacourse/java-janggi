import infrastructure.DatabaseManager;
import io.GameConsole;

public class Application {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.initSchema();

        GameConsole gameConsole = new GameConsole();
        gameConsole.run();
    }
}
