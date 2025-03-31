import controller.InitializerController;
import controller.JanggiController;
import db.MySQLConnection;
import db.dao.JanggiGameDao;
import service.JanggiGameService;
import service.initializer.JanggiGameInitializer;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiGameService gameService;
        InitializerController initializerController = new InitializerController(inputView, new JanggiGameInitializer(),
                new JanggiGameDao(MySQLConnection.getInstance()));

        Long gameId = initializerController.getGameId();
        gameService = new JanggiGameService(gameId);

        JanggiController controller = new JanggiController(gameService, inputView, outputView);

        while (!gameService.isFinished()) {
            try {
                controller.printBoard();
                controller.selectOption();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        controller.printGameResult();
    }
}
