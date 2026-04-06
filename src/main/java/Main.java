import controller.JanggiController;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class Main {
    public static void main(String[] args) {
        JanggiController janggiController = new JanggiController(
                new InputView(),
                new OutputView(),
                new JanggiService()
        );
        janggiController.run();
    }
}
