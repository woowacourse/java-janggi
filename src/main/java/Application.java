import config.db.DatabaseConfig;
import controller.JanggiController;
import repository.JanggiRepositoryImpl;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        DatabaseConfig.initSchema();
        JanggiService janggiService = new JanggiService(new JanggiRepositoryImpl());
        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), janggiService);
        janggiController.run();
    }
}
