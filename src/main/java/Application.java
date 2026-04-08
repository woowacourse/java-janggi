import controller.JanggiController;
import database.SQLiteConnectionProvider;
import repository.BoardJdbcRepository;
import repository.GameJdbcRepository;
import service.BoardService;
import service.GameService;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new JanggiService(
                        new BoardService(
                                new BoardJdbcRepository()
                        ),
                        new GameService(
                                new GameJdbcRepository()
                        ),
                        new SQLiteConnectionProvider()
                )
        );
        janggiController.start();
    }
}
