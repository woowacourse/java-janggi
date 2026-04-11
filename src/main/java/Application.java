import config.db.DatabaseConfig;
import controller.JanggiController;
import repository.JanggiRepositoryImpl;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        try {
            run();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void run() {
        DatabaseConfig.initSchema();
        JanggiService janggiService = new JanggiService(new JanggiRepositoryImpl(DatabaseConfig.getConnection()));
        JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), janggiService);
        janggiController.run();
    }
}
