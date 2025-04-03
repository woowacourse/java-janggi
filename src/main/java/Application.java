import controller.JanggiController;
import dao.BoardDao;
import dao.DatabaseConnection;
import dao.Executor;
import service.GameService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Executor executor = new Executor(databaseConnection);
        BoardDao boardDao = new BoardDao(executor);
        GameService gameService = new GameService(boardDao);

        JanggiController controller = new JanggiController(gameService, inputView, outputView);
        controller.run();
    }
}
