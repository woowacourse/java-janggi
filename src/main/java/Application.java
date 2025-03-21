import janggi.controller.JanggiController;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiController janggiController = new JanggiController(inputView, outputView);
        janggiController.run();
    }
}
