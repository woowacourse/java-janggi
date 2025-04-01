import controller.JanggiController;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        JanggiMigration flyMigration = new JanggiMigration(
                JanggiMigration.URL,
                JanggiMigration.USERNAME,
                JanggiMigration.PASSWORD
        );
        flyMigration.migrate();

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiController controller = new JanggiController(inputView, outputView);

        controller.startJanggiGame();
    }
}
