import database.H2ConsoleStarter;
import database.InitTable;
import repository.GameRepository;

public class Application {
    public static void main(String[] args) {
        H2ConsoleStarter.start();
        InitTable.schemaInit();

        GameRepository gameRepository = new GameRepository();
        GameService gameService = new GameService(gameRepository);
        GameController gameController = new GameController(gameService);

        gameController.start();
    }
}
