import application.GameInitializer;
import application.GamePersistenceService;
import controller.JanggiController;
import domain.JanggiGame;

public class Application {

    public static void main(String[] args) {
        GameInitializer gameInitializer = new GameInitializer();
        gameInitializer.initializeDatabase();

        GamePersistenceService gamePersistenceService = gameInitializer.createGamePersistenceService();
        JanggiGame janggiGame = gamePersistenceService.loadOrCreate();
        JanggiController janggiController = new JanggiController(janggiGame, gamePersistenceService);

        janggiController.run();
    }
}
