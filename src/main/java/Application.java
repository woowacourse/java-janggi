import database.H2ConsoleStarter;
import database.InitTable;
import repository.GameRepository;
import repository.PieceRepository;

public class Application {
    public static void main(String[] args) {
        H2ConsoleStarter.start();
        InitTable.schemaInit();

        GameRepository gameRepository = new GameRepository();
        PieceRepository pieceRepository = new PieceRepository();
        GameService gameService = new GameService(gameRepository, pieceRepository);
        GameController gameController = new GameController(gameService);

        gameController.start();
    }
}
