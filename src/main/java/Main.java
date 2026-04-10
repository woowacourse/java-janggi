import database.ConnectionManager;
import database.DatabaseConfig;
import database.DatabaseInitializer;
public class Main {

    public static void main(String[] args) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(new ConnectionManager(new DatabaseConfig()));
        databaseInitializer.initialize();

        new GameRunner().run();
    }
}
