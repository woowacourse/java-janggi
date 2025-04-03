import controller.JanggiController;
import dao.BoardDao;
import dao.DatabaseConnection;
import dao.DatabaseExecutor;
import dao.GameStateDao;
import service.GameService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        DatabaseExecutor databaseExecutor = new DatabaseExecutor(databaseConnection);
        BoardDao boardDao = new BoardDao(databaseExecutor);
        GameStateDao gameStateDao = new GameStateDao(databaseExecutor);
        GameService gameService = new GameService(boardDao, gameStateDao);

        JanggiController controller = new JanggiController(gameService, inputView, outputView);
        controller.run();
    }
}
