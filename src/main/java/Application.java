import controller.JanggiController;
import service.JanggiService;
import util.ProductionDatabaseConnector;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        JanggiController controller = new JanggiController(
                new InputView(),
                new OutputView(),
                new JanggiService()
        );
        controller.run();
    }
}
