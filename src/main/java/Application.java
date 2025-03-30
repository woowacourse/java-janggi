import controller.JanggiController;
import dao.BoardDao;
import dao.UserDao;
import service.GameService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        UserDao userDao = new UserDao();
        BoardDao boardDao = new BoardDao(userDao);
        GameService gameService = new GameService(boardDao);

        JanggiController controller = new JanggiController(gameService, inputView, outputView);
        controller.run();
    }
}
