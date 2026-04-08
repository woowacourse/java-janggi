import controller.JanggiController;
import database.SQLiteConnectionProvider;
import repository.BoardJdbcRepository;
import repository.GameJdbcRepository;
import service.BoardService;
import service.FacadeService;
import service.GameService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new FacadeService(
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
