import database.ConnectionManager;
import database.DatabaseConfig;
import database.DatabaseInitializer;

public class Main {

    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(new ConnectionManager(databaseConfig));
        databaseInitializer.initialize();

        new GameRunner().run();
    }
}

