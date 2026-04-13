import application.GameInitializer;
import application.GamePersistenceService;
import controller.JanggiController;

public class Application {

    public static void main(String[] args) {
        GameInitializer gameInitializer = new GameInitializer();
        gameInitializer.initializeDatabase();

        GamePersistenceService gamePersistenceService = gameInitializer.createGamePersistenceService();
        JanggiController janggiController = new JanggiController(gamePersistenceService);

        janggiController.run();
    }
    
}
