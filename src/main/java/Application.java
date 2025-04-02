import controller.JanggiController;
import db.MySqlDatabaseConnector;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        final JanggiService janggiService = new JanggiService(new MySqlDatabaseConnector());

        final JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), janggiService);
        janggiController.run();
    }
}
