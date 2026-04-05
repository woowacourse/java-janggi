import controller.JanggiController;
import database.SQLiteConnectionProvider;
import repository.GameJdbcRepository;
import repository.JanggiJdbcRepository;
import service.FacadeService;
import service.GameService;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new FacadeService(
                        new JanggiService(
                                new JanggiJdbcRepository()
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
